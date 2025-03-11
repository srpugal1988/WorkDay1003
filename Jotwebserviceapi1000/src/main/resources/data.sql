USE `jcms`;

/*Data for the table `sm_logindetails` */

insert  into `sm_logindetails`(`id`,`name`,`logintime`,`logouttime`,`dateadded`) values 
(4,'p1','2022-11-22 11:57:57',NULL,'2022-11-22'),
(5,'p1',NULL,'2022-11-22 11:58:28','2022-11-22'),
(6,'n1','2022-11-22 12:03:58',NULL,'2022-11-22'),
(7,'p1','2022-11-22 14:47:02',NULL,'2022-11-22'),
(8,'p1','2022-11-22 14:47:02',NULL,'2022-11-22'),
(9,'p1','2022-11-22 15:41:27',NULL,'2022-11-22'),
(10,'p1',NULL,'2022-11-22 15:42:16','2022-11-22'),
(11,'p1','2022-11-22 15:45:01',NULL,'2022-11-22'),
(12,'b1','2022-11-22 17:26:49',NULL,'2022-11-22'),
(13,'p1','2022-11-22 17:29:45',NULL,'2022-11-22'),
(14,'p1','2022-11-22 17:41:39',NULL,'2022-11-22'),
(15,'p1','2022-11-22 17:44:52',NULL,'2022-11-22'),
(16,'p1','2022-11-23 10:37:30',NULL,'2022-11-23'),
(17,'p1','2022-11-23 10:38:47',NULL,'2022-11-23'),
(18,'p1','2022-11-23 10:39:11',NULL,'2022-11-23'),
(19,'p1','2022-11-23 10:41:08',NULL,'2022-11-23'),
(20,'n1','2022-11-23 10:52:11',NULL,'2022-11-23'),
(21,'n1',NULL,'2022-11-23 10:52:32','2022-11-23'),
(22,'p1','2022-11-23 10:54:22',NULL,'2022-11-23'),
(23,'p1',NULL,'2022-11-23 10:54:33','2022-11-23');

/*Data for the table `sm_userdetails` */

insert  into `sm_userdetails`(`id`,`userrefno`,`firstname`,`lastname`,`fullname`,`email`,`contactnumber`,`address1`,`address2`,`address3`,`city`,`state`,`zip`,`country`,`roleid`,`rolename`,`jobtitle`,`username`,`password`,`online`,`dateadded`,`createdby`,`isactiveuser`,`dbname`,`clientname`) values 
(1,'USREF1001','Balraj','Yesu','Balraj Yesu','balraj.s@gmail.com','9154812345',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'ADMIN','Software Engineer','b1','p1',1,'1970-01-01 10:41:25','Administrator',1,'jcms','tecode'),
(2,'USREF1002','Noorul','Ameen','Noorul Ameen','noorul.a@gmail.com','2545184456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'ADMIN','Technology Architect','n1','p1',0,'1970-01-30 10:41:32','Administrator',1,'jcms','tecode'),
(3,'USREF1003','Pugal','Srp','Pugal Srp','pugal.srp@gmail.com','9548544444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'ADMIN','Accountant','p1','p1',0,'2020-01-01 10:53:51','Administrator',1,'jcms','tecode');
