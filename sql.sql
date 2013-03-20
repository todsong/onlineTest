create table t_dept
(
    id int not null auto_increment,
    name varchar(150),
	primary key(id)
);

create table T_USER(
    id varchar(50) not null,
    passwd varchar(50) not null,
    name varchar(24) not null,
	dept int not null,
    telephone varchar(50),
	status varchar(1),
    primary key(id)
);
ALTER TABLE T_USER add foreign key(dept) references t_dept(id); 

create table T_Admin
(
    id varchar(50) not null,
    passwd varchar(50) not null,
	primary key(id)
);
insert into T_Admin values('admin','f6fdffe48c908deb0f4c3bd36c032e72');
insert into T_Admin values('admin2','af8eb328301d219cfd1d50e6c6a48f58');

create table t_subject
(
    id int not null auto_increment,
    name varchar(150),
	primary key(id)
);

create table T_Exam
(
    id int not null auto_increment,
    examName varchar(150) not null,
    startTime varchar(15),
    endTime varchar(15),
	examType varchar(1),
	singleNum int,
	singleScore int,
	multiNum int,
	multiScore int,
	judgeNum int,
	judgeScore int,
	passScore int,
	subjectId int,
    primary key(id)
);
ALTER TABLE T_EXAM add foreign key(subjectId) references T_SUBJECT(id); 

create table T_SINGLE_QUES
(
    id int not null auto_increment,
    qName varchar(900) not null,
    qAnswer varchar(1) not null,
	optionA varchar(900),
	optionB varchar(900),
	optionC varchar(900),
	optionD varchar(900),
	optionE varchar(900),
	optNum int,
	subjectId int,
	hash varchar(50) unique,
	status int,
	primary key(id)
);

create table T_MULTI_QUES(
    id int not null auto_increment,
    qName varchar(900) not null,
    qAnswer varchar(6) not null,
	optionA varchar(900),
	optionB varchar(900),
	optionC varchar(900),
	optionD varchar(900),
	optionE varchar(900),
	optNum int,
	subjectId int,
	hash varchar(50) unique,
	status int,
    primary key(id)
);

create table T_JUDGE_QUES(
    id int not null auto_increment,
    qName varchar(900) not null,
    qAnswer varchar(1) not null,
	subjectId int,
	hash varchar(50) unique,
	status int,
    primary key(id)
);
alter table T_JUDGE_QUES  add foreign key(subjectId) references T_SUBJECT(id); 
alter table T_SINGLE_QUES  add foreign key(subjectId) references T_SUBJECT(id); 
alter table T_MULTI_QUES  add foreign key(subjectId) references T_SUBJECT(id); 

create table T_PRAC_QUES(
    pracId int,
	caseId int,
	singleIdList varchar(600),
	multiIdList varchar(600),
	judgeIdList varchar(600),
	primary key(pracId, caseId)
);
alter table T_PRAC_QUES add foreign key(pracId) references T_EXAM(id); 

create table T_USER_EXAM(
    examId int,
	userId varchar(50),
	score int,
	singleIdList varchar(600),
	multiIdList varchar(600),
	judgeIdList varchar(600),
	singleAnswerList varchar(200),
	multiAnswerList varchar(1000),
	judgeAnswerList varchar(200),
	primary key(examId, userId)
);

alter table T_USER_EXAM  add foreign key(examId) references T_EXAM(id); 
alter table T_USER_EXAM  add foreign key(userId) references T_USER(id); 

--2013年3月3日，为支持一exam多subject
alter table t_exam drop foreign key t_exam_ibfk_1;
alter table t_exam modify column subjectId varchar(30);
alter table t_exam modify column judgeNum varchar(30);
alter table t_exam modify column multiNum varchar(30);
alter table t_exam modify column singleNum varchar(30);

