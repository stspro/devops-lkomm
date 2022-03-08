resource "aws_lb" "loadbalancer" {
  internal            = "${var.internal}"  # internal = true else false
  name                = "openapi-alb-name"
  subnets             = ["${var.subnet1}", "${var.subnet2}"] # enter the private subnet 
  security_groups     = ["sg-01849003c4f9203ca"] #CHANGE THIS
}


resource "aws_lb_target_group" "lb_target_group" {
  name        = "openapi-target-alb-name"
  port        = "80"
  protocol    = "HTTP"
  vpc_id      = "vpc-000851116d62e0c13" # CHNAGE THIS
  target_type = "ip"


#STEP 1 - ECS task Running
  health_check {
    healthy_threshold   = "3"
    interval            = "10"
    port                = "8080"
    path                = "/index.html"
    protocol            = "HTTP"
    unhealthy_threshold = "3"
  }
}

resource "aws_lb_listener" "lb_listener" {
  "default_action" {
    target_group_arn = "${aws_lb_target_group.lb_target_group.id}"
    type             = "forward"
  }

  #certificate_arn   = "arn:aws:acm:us-east-1:689019322137:certificate/9fcdad0a-7350-476c-b7bd-3a530cf03090"
  load_balancer_arn = "${aws_lb.loadbalancer.arn}"
  port              = "80"
  protocol          = "HTTP"
}
