# PostFix

## 工具 


sudo yum install sendmail


## 配置文件 /etc/postfix/main.cf

```config
myhostname = mail.wwj89.fun
mydomain = wwj89.fun
myorigin = $mydomain
inet_interfaces = all
mydestination = $myhostname, localhost.$mydomain, localhost, $mydomain
mynetworks_style = subnet
mynetworks = 127.0.0.0/8
home_mailbox = Maildir/
smtpd_sasl_auth_enable = yes


```