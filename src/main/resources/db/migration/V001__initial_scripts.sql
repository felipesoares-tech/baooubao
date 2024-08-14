create table category_entity (id bigint not null auto_increment, title varchar(100),icon varchar(255),active bit, created_at datetime(6) not null, primary key (id)) engine=InnoDB;
create table cycle_entity (id bigint not null auto_increment,finish_date date not null, start_date date not null, title varchar(100) not null,active bit,created_at datetime(6) not null, primary key (id)) engine=InnoDB;
create table proposal_entity (id bigint not null auto_increment, likes integer, category_entity_id bigint, cycle_entity_id bigint, user_entity_id bigint, title varchar(100), video_url varchar(100), description varchar(255), image LONGBLOB, situation enum ('OPEN_FOR_VOTING','FORWARDED_TO_BOARD','APPROVED','DENIED','PENDING_MODERATION'),active bit,created_at datetime(6), primary key (id)) engine=InnoDB;
create table role_entity (id bigint not null auto_increment, name enum ('ROLE_USER','ROLE_ADMINISTRATOR'), primary key (id)) engine=InnoDB;
create table user_roles (role_id bigint not null, user_id bigint not null) engine=InnoDB;
create table user_entity (id bigint not null auto_increment, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, type enum ('DOCENTE','TAE','ESTUDANTE') not null,active bit,created_at datetime(6) not null, primary key (id)) engine=InnoDB;
create table voting_entity (id bigint not null auto_increment,created_at datetime(6) not null,proposal_entity_id bigint, user_entity_id bigint, primary key (id)) engine=InnoDB;
create table confirmation_token_entity (id bigint not null auto_increment,created_date datetime(6), expiry_date datetime(6), user_id bigint, token varchar(255), primary key (id)) engine=InnoDB;
alter table if exists confirmation_token_entity add constraint FKf7xm48x2cxyrv023e4lkr18ld foreign key (user_id) references user_entity (id);
alter table if exists user_entity add constraint UK_4xad1enskw4j1t2866f7sodrx unique (email);
alter table if exists proposal_entity add constraint FK925c4prmhditn6tm5hjuos6yd foreign key (category_entity_id) references category_entity (id);
alter table if exists proposal_entity add constraint FKqbt0am5xum8apf1maor4mkr5v foreign key (cycle_entity_id) references cycle_entity (id);
alter table if exists proposal_entity add constraint FK163r4q2hehstgwey6lqvj9bib foreign key (user_entity_id) references user_entity (id);
alter table if exists user_roles add constraint FKh83ux1f9i6ch6gie5xtj5mqnt foreign key (role_id) references role_entity (id);
alter table if exists user_roles add constraint FK6y02653x6ebhsu2plf21ard62 foreign key (user_id) references user_entity (id);
alter table if exists voting_entity add constraint FKro9nt8tj8srxqfqq462esu7eo foreign key (proposal_entity_id) references proposal_entity (id);
alter table if exists voting_entity add constraint FKhp3l3cm3brs4t6b0cp48r59m1 foreign key (user_entity_id) references user_entity (id);