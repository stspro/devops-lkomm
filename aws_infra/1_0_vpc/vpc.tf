# one vpc to hold them all, and in the cloud bind them
resource "aws_vpc" "nugen_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags {
    Name = "nugen-vpc"
  }
}

# let vpc talk to the internet - create internet gateway 
resource "aws_internet_gateway" "nugen_gw" {
  vpc_id = "${aws_vpc.nugen_vpc.id}"
  tags {
    Name = "nugen-igw"
  }
}

# create one public subnet per availability zone
resource "aws_subnet" "nugen_public_subnet" {
  availability_zone       = "${element(var.azs,count.index)}"
  cidr_block              = "${element(var.public_subnets_cidr,count.index)}"
  count                   = "${length(var.azs)}"
  map_public_ip_on_launch = true
  vpc_id                  = "${aws_vpc.nugen_vpc.id}"
  tags {
    Name = "subnet-pub-${count.index}"
  }
}

# create one private subnet per availability zone
resource "aws_subnet" "nugen_private_subnet" {
  availability_zone       = "${element(var.azs,count.index)}"
  cidr_block              = "${element(var.private_subnets_cidr,count.index)}"
  count                   = "${length(var.azs)}"
  map_public_ip_on_launch = true
  vpc_id                  = "${aws_vpc.nugen_vpc.id}"
  tags {
    Name = "subnet-priv-${count.index}"
  }
}

# dynamic list of the public subnets created above
data "aws_subnet_ids" "public" {
  depends_on = ["aws_subnet.nugen_public_subnet"]
  vpc_id     = "${aws_vpc.nugen_vpc.id}"
}

# dynamic list of the private subnets created above
data "aws_subnet_ids" "private" {
  depends_on = ["aws_subnet.nugen_private_subnet"]
  vpc_id     = "${aws_vpc.nugen_vpc.id}"
}

# main route table for vpc and subnets
resource "aws_route_table" "public" {
  vpc_id = "${aws_vpc.nugen_vpc.id}"
  tags {
    Name = "public_route_table_main"
  }
}

# add public gateway to the route table
resource "aws_route" "public" {
  gateway_id             = "${aws_internet_gateway.nugen_gw.id}"
  destination_cidr_block = "0.0.0.0/0"
  route_table_id         = "${aws_route_table.public.id}"
}

# associate route table with vpc
resource "aws_main_route_table_association" "public" {
  vpc_id         = "${aws_vpc.nugen_vpc.id}"
  route_table_id = "${aws_route_table.public.id}"
}

# and associate route table with each subnet
resource "aws_route_table_association" "public" {
  count           = "${length(var.azs)}"
  subnet_id      = "${element(data.aws_subnet_ids.public.ids, count.index)}"
  route_table_id = "${aws_route_table.public.id}"
}

# create elastic IP (EIP) to assign it the NAT Gateway 
resource "aws_eip" "nugen_eip" {
  count    = "${length(var.azs)}"
  vpc      = true
  depends_on = ["aws_internet_gateway.nugen_gw"]
}

# create NAT Gateways
# make sure to create the nat in a internet-facing subnet (public subnet)
resource "aws_nat_gateway" "nugen_nat_gw" {
    count    = "${length(var.azs)}"
    allocation_id = "${element(aws_eip.nugen_eip.*.id, count.index)}"
    subnet_id = "${element(aws_subnet.nugen_public_subnet.*.id, count.index)}"
    depends_on = ["aws_internet_gateway.nugen_gw"]
}

# for each of the private ranges, create a "private" route table.
resource "aws_route_table" "private" {
  vpc_id = "${aws_vpc.nugen_vpc.id}"
  count ="${length(var.azs)}" 
  tags { 
    Name = "private_subnet_route_table_${count.index}"
  }
}

# add a nat gateway to each private subnet's route table
resource "aws_route" "private_nat_gateway_route" {
  count = "${length(var.azs)}"
  route_table_id = "${element(aws_route_table.private.*.id, count.index)}"
  destination_cidr_block = "0.0.0.0/0"
  depends_on = ["aws_route_table.private"]
  nat_gateway_id = "${element(aws_nat_gateway.nugen_nat_gw.*.id, count.index)}"
}

