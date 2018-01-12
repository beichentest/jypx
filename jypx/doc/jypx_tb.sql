-----------------------------------------------------
-- Export file for user HYSH                         --
-- Created by Administrator on 2018/1/12, 10:09:31 --
-----------------------------------------------------

spool jypx_tb.log

prompt
prompt Creating table JYPX_ADMIN
prompt =========================
prompt
create table JYPX_ADMIN
(
  USERID     VARCHAR2(32) not null,
  USERNAME   VARCHAR2(50) not null,
  PASSWORD   VARCHAR2(50) not null,
  STATE      NUMBER(1) default 1,
  SALT       VARCHAR2(32),
  IS_SYSTEM  NUMBER(1) default 0,
  CREATED_AT DATE,
  UPDATED_AT DATE
)
;
alter table JYPX_ADMIN
  add constraint PK_JYPX_ADMIN primary key (USERID);
create unique index UNIQUE_JYPX_ADMIN on JYPX_ADMIN (USERNAME);

prompt
prompt Creating table JYPX_ADMIN_ROLE
prompt ==============================
prompt
create table JYPX_ADMIN_ROLE
(
  ADMIN_ID VARCHAR2(32) not null,
  ROLE_ID  VARCHAR2(32) not null
)
;
alter table JYPX_ADMIN_ROLE
  add primary key (ADMIN_ID, ROLE_ID);

prompt
prompt Creating table JYPX_CMS_INFO
prompt ============================
prompt
create table JYPX_CMS_INFO
(
  INFO_ID     VARCHAR2(36) not null,
  INFO_NAME   VARCHAR2(128),
  MODULE_ID   VARCHAR2(12),
  IMG_URL     VARCHAR2(128),
  IS_OPEN     CHAR(1),
  LINK_URL    VARCHAR2(256),
  CREATE_DATE DATE,
  UPDTE_DATE  DATE,
  CODE        VARCHAR2(36),
  REMARK      VARCHAR2(128),
  CONTENT     CLOB,
  ORG_ID      VARCHAR2(128),
  AUTHOR      VARCHAR2(36),
  CREATE_USER VARCHAR2(36),
  TAG         VARCHAR2(64),
  SUMMARY     VARCHAR2(512),
  DEL_FLAG    CHAR(1)
)
;
comment on table JYPX_CMS_INFO
  is '������Ϣ';
comment on column JYPX_CMS_INFO.INFO_ID
  is 'ID';
comment on column JYPX_CMS_INFO.INFO_NAME
  is '����';
comment on column JYPX_CMS_INFO.MODULE_ID
  is 'ģ��ID';
comment on column JYPX_CMS_INFO.IMG_URL
  is 'ͼƬURL';
comment on column JYPX_CMS_INFO.IS_OPEN
  is '�Ƿ񿪷� 0����1����';
comment on column JYPX_CMS_INFO.LINK_URL
  is '���ӵ�ַ';
comment on column JYPX_CMS_INFO.CREATE_DATE
  is '��������';
comment on column JYPX_CMS_INFO.UPDTE_DATE
  is '�޸�����';
comment on column JYPX_CMS_INFO.CODE
  is '���';
comment on column JYPX_CMS_INFO.REMARK
  is '��ע';
comment on column JYPX_CMS_INFO.CONTENT
  is '����';
comment on column JYPX_CMS_INFO.ORG_ID
  is '���ƻ���';
comment on column JYPX_CMS_INFO.AUTHOR
  is '����/��ʦ';
comment on column JYPX_CMS_INFO.CREATE_USER
  is '������';
comment on column JYPX_CMS_INFO.TAG
  is '��ǩ';
comment on column JYPX_CMS_INFO.SUMMARY
  is '���';
comment on column JYPX_CMS_INFO.DEL_FLAG
  is '�Ƿ�ɾ�� 0����1����';
alter table JYPX_CMS_INFO
  add constraint PK_JYPX_CMS_INFO primary key (INFO_ID);

prompt
prompt Creating table JYPX_CMS_MODULE
prompt ==============================
prompt
create table JYPX_CMS_MODULE
(
  MODULE_ID   VARCHAR2(36) not null,
  MODULE_NAME VARCHAR2(50),
  PARENT_ID   VARCHAR2(36)
)
;
comment on table JYPX_CMS_MODULE
  is '��Ϣ��Ŀ';
comment on column JYPX_CMS_MODULE.MODULE_ID
  is 'ģ��ID';
comment on column JYPX_CMS_MODULE.MODULE_NAME
  is 'ģ������';
comment on column JYPX_CMS_MODULE.PARENT_ID
  is '��ID';
alter table JYPX_CMS_MODULE
  add constraint PK_JYPX_CMS_MODULE primary key (MODULE_ID);

prompt
prompt Creating table JYPX_LOG
prompt =======================
prompt
create table JYPX_LOG
(
  LOG_ID     VARCHAR2(32) not null,
  LOG_USER   VARCHAR2(32),
  LOG_TIME   DATE,
  LOG_IP     VARCHAR2(15),
  LOG_ACTION VARCHAR2(255)
)
;
alter table JYPX_LOG
  add primary key (LOG_ID);

prompt
prompt Creating table JYPX_MEMBER
prompt ==========================
prompt
create table JYPX_MEMBER
(
  USERID     VARCHAR2(32) not null,
  ACCOUNT    VARCHAR2(20) not null,
  PASSWORD   VARCHAR2(50) not null,
  SALT       VARCHAR2(32) not null,
  STATE      NUMBER(1) default 1,
  CREATED_AT DATE not null,
  UPDATED_AT DATE not null
)
;
alter table JYPX_MEMBER
  add primary key (USERID, ACCOUNT);

prompt
prompt Creating table JYPX_MENU
prompt ========================
prompt
create table JYPX_MENU
(
  MENU_ID    VARCHAR2(32) not null,
  MENU_NAME  VARCHAR2(50) not null,
  MENU_TYPE  VARCHAR2(10) not null,
  MENU_URL   VARCHAR2(50) not null,
  MENU_CODE  VARCHAR2(50) not null,
  PARENT_ID  VARCHAR2(32) not null,
  PARENT_IDS VARCHAR2(255),
  CHILD_NUM  NUMBER(10) default 0,
  LISTORDER  NUMBER(10) default 0,
  CREATED_AT DATE not null,
  UPDATED_AT DATE not null
)
;
comment on column JYPX_MENU.MENU_TYPE
  is '��Դ���ͣ��˵��򶼰�ť(menu,button)';
alter table JYPX_MENU
  add primary key (MENU_ID);

prompt
prompt Creating table JYPX_ROLE
prompt ========================
prompt
create table JYPX_ROLE
(
  ROLE_ID    VARCHAR2(32) not null,
  ROLE_NAME  VARCHAR2(20) not null,
  ROLE_DESC  VARCHAR2(100) not null,
  ENABLE     NUMBER(1) default 1,
  CREATED_AT DATE not null,
  UPDATED_AT DATE not null
)
;
alter table JYPX_ROLE
  add primary key (ROLE_ID);

prompt
prompt Creating table JYPX_ROLE_MENU
prompt =============================
prompt
create table JYPX_ROLE_MENU
(
  ROLE_ID VARCHAR2(32) not null,
  MENU_ID VARCHAR2(32) not null
)
;
alter table JYPX_ROLE_MENU
  add primary key (ROLE_ID, MENU_ID);

prompt
prompt Creating table KJK_COST
prompt =======================
prompt
create table KJK_COST
(
  COST_ID      VARCHAR2(32) not null,
  CW_ID        INTEGER,
  EXPERT_ID    VARCHAR2(12),
  COST         INTEGER,
  CARD_NO      VARCHAR2(36),
  OPENING_BANK VARCHAR2(50),
  SYSTEM       VARCHAR2(32),
  PAY_DESC     VARCHAR2(200),
  APPLY_TIME   DATE default sysdate,
  PAY_TIME     DATE,
  AUDIT_STATUS VARCHAR2(32),
  OPERATOR     VARCHAR2(32),
  STATUS       CHAR(1) default 0,
  REMARK       VARCHAR2(100),
  EXPERT_NAME  VARCHAR2(50),
  ID_CARD      VARCHAR2(50),
  MOBILE       VARCHAR2(50),
  CWARE_NAME   VARCHAR2(50)
)
;
comment on column KJK_COST.CW_ID
  is '�μ�ID';
comment on column KJK_COST.EXPERT_ID
  is 'ר��ID';
comment on column KJK_COST.COST
  is '�������';
comment on column KJK_COST.CARD_NO
  is '���п���';
comment on column KJK_COST.OPENING_BANK
  is '������';
comment on column KJK_COST.SYSTEM
  is '������Ŀ(��ӦKJK_DIC��SYSTEM)';
comment on column KJK_COST.PAY_DESC
  is '֧������';
comment on column KJK_COST.APPLY_TIME
  is '����ʱ��';
comment on column KJK_COST.PAY_TIME
  is '֧��ʱ��';
comment on column KJK_COST.AUDIT_STATUS
  is '���״̬(0 δ��ˣ�1 �����)';
comment on column KJK_COST.OPERATOR
  is '������';
comment on column KJK_COST.STATUS
  is '����״̬(0 ������1 ʧЧ)';
comment on column KJK_COST.EXPERT_NAME
  is 'ר������';
comment on column KJK_COST.ID_CARD
  is '���֤��';
comment on column KJK_COST.MOBILE
  is '�ֻ���';
comment on column KJK_COST.CWARE_NAME
  is '�μ�����';
alter table KJK_COST
  add constraint PK_KJK_COST primary key (COST_ID);

prompt
prompt Creating table KJK_COURSEWARE
prompt =============================
prompt
create table KJK_COURSEWARE
(
  ID             INTEGER not null,
  NAME           VARCHAR2(300),
  P_NAME         VARCHAR2(300),
  CODE           VARCHAR2(100),
  PATH           VARCHAR2(300),
  FILE_TYPE      VARCHAR2(50),
  PLAY_TYPE      VARCHAR2(2),
  SUBJECT        VARCHAR2(50),
  KEYWORD        VARCHAR2(200),
  INTRODUCE      VARCHAR2(2000),
  EXPERT         VARCHAR2(100),
  EXPERT_UNIT    VARCHAR2(100),
  SOURCE         VARCHAR2(200),
  CREATE_DATE    DATE default sysdate not null,
  ADD_DATE       DATE default sysdate not null,
  UPDATE_DATE    DATE default sysdate not null,
  CLICK_COUNT    INTEGER default 0 not null,
  STATUS         INTEGER default 0 not null,
  CLASS_TIME     NUMBER(10,2),
  CLASS_HOUR     NUMBER(10,2),
  REMARK         VARCHAR2(1000),
  PAR1           VARCHAR2(2000),
  PAR2           VARCHAR2(2000),
  PAR3           VARCHAR2(2000),
  PAR4           VARCHAR2(2000),
  MOBILE_TYPE    VARCHAR2(2),
  SUBJECT2       VARCHAR2(50),
  SL_IMG         VARCHAR2(250),
  LOGO           VARCHAR2(200),
  PIANTOU        VARCHAR2(200),
  PIANWEI        VARCHAR2(200),
  SHOT_YEAR      VARCHAR2(12),
  CLASS_TIME_STR VARCHAR2(50),
  LABEL          VARCHAR2(500),
  PLAY_FLAG      CHAR(1),
  CREATER        VARCHAR2(32),
  MODIFIER       VARCHAR2(32),
  PROJECT_LEVEL  CHAR(1)
)
;
comment on column KJK_COURSEWARE.ID
  is '�μ�ID';
comment on column KJK_COURSEWARE.NAME
  is '�μ�����';
comment on column KJK_COURSEWARE.P_NAME
  is '��Ŀ����';
comment on column KJK_COURSEWARE.CODE
  is '���';
comment on column KJK_COURSEWARE.PATH
  is '·��';
comment on column KJK_COURSEWARE.FILE_TYPE
  is '�ļ�����';
comment on column KJK_COURSEWARE.PLAY_TYPE
  is '�������ͣ�PC��';
comment on column KJK_COURSEWARE.SUBJECT
  is '����ѧ��';
comment on column KJK_COURSEWARE.KEYWORD
  is '�ؼ���';
comment on column KJK_COURSEWARE.INTRODUCE
  is '���';
comment on column KJK_COURSEWARE.EXPERT
  is 'ר��';
comment on column KJK_COURSEWARE.EXPERT_UNIT
  is 'ר�ҵ�λ';
comment on column KJK_COURSEWARE.SOURCE
  is '��Դ';
comment on column KJK_COURSEWARE.CREATE_DATE
  is '����ʱ��';
comment on column KJK_COURSEWARE.ADD_DATE
  is '���ʱ��';
comment on column KJK_COURSEWARE.UPDATE_DATE
  is '�޸�ʱ��';
comment on column KJK_COURSEWARE.CLICK_COUNT
  is '�����';
comment on column KJK_COURSEWARE.STATUS
  is '״̬ -1�¼�,0��Ч,1ɾ��';
comment on column KJK_COURSEWARE.CLASS_TIME
  is 'ʱ��(��)';
comment on column KJK_COURSEWARE.CLASS_HOUR
  is '��ʱ/ѧʱ';
comment on column KJK_COURSEWARE.REMARK
  is '��ע';
comment on column KJK_COURSEWARE.PAR1
  is '����1';
comment on column KJK_COURSEWARE.PAR2
  is '����2';
comment on column KJK_COURSEWARE.PAR3
  is '����3';
comment on column KJK_COURSEWARE.PAR4
  is '����4';
comment on column KJK_COURSEWARE.MOBILE_TYPE
  is '�������ͣ��ֻ���';
comment on column KJK_COURSEWARE.SUBJECT2
  is '����ѧ��';
comment on column KJK_COURSEWARE.SL_IMG
  is '����ͼ';
comment on column KJK_COURSEWARE.LOGO
  is 'logo';
comment on column KJK_COURSEWARE.PIANTOU
  is 'Ƭͷ';
comment on column KJK_COURSEWARE.PIANWEI
  is 'Ƭβ';
comment on column KJK_COURSEWARE.SHOT_YEAR
  is '�������';
comment on column KJK_COURSEWARE.CLASS_TIME_STR
  is 'ʱ��(ʱ:��:��)';
comment on column KJK_COURSEWARE.LABEL
  is '��ǩ';
comment on column KJK_COURSEWARE.PLAY_FLAG
  is '���ѱ�־��0 δ���ѣ�1 ���ѣ�';
comment on column KJK_COURSEWARE.CREATER
  is '�μ�������id';
comment on column KJK_COURSEWARE.MODIFIER
  is '�μ�����޸���id';
comment on column KJK_COURSEWARE.PROJECT_LEVEL
  is '��Ŀ����0 ��ͨ��Ŀ��1 ���Ҽ���Ŀ��';
alter table KJK_COURSEWARE
  add constraint PK_KJK_COURSEWARE primary key (ID);

prompt
prompt Creating table KJK_CW_PLAY_TYPE
prompt ===============================
prompt
create table KJK_CW_PLAY_TYPE
(
  ID        INTEGER not null,
  CW_ID     INTEGER,
  PLAY_TYPE VARCHAR2(2),
  PAR1      VARCHAR2(2000),
  PAR2      VARCHAR2(2000),
  PAR3      VARCHAR2(2000),
  PAR4      VARCHAR2(2000)
)
;
comment on column KJK_CW_PLAY_TYPE.PAR1
  is '����1';
comment on column KJK_CW_PLAY_TYPE.PAR2
  is '����2';
comment on column KJK_CW_PLAY_TYPE.PAR3
  is '����3';
comment on column KJK_CW_PLAY_TYPE.PAR4
  is '����4';
alter table KJK_CW_PLAY_TYPE
  add constraint PK_KJK_CW_PLAY_TYPE primary key (ID);

prompt
prompt Creating table KJK_DIC
prompt ======================
prompt
create table KJK_DIC
(
  DIC_ID    VARCHAR2(32) not null,
  DIC_DESC  VARCHAR2(100),
  DIC_TYPE  VARCHAR2(100),
  PARENT_ID VARCHAR2(32) default 0,
  SEQ       NUMBER(6) default 0,
  STATUS    CHAR(1) default 0,
  REMARK    VARCHAR2(100)
)
;
comment on column KJK_DIC.DIC_DESC
  is '����';
comment on column KJK_DIC.DIC_TYPE
  is '����';
comment on column KJK_DIC.PARENT_ID
  is '��ID';
comment on column KJK_DIC.SEQ
  is '����';
comment on column KJK_DIC.STATUS
  is '����״̬(0 ����,1 ʧЧ)';
comment on column KJK_DIC.REMARK
  is '��ע';
alter table KJK_DIC
  add constraint PK_KJK_DIC primary key (DIC_ID);

prompt
prompt Creating table KJK_QUESTION
prompt ===========================
prompt
create table KJK_QUESTION
(
  Q_ID         VARCHAR2(32) not null,
  CW_ID        INTEGER,
  Q_TYPE       VARCHAR2(32),
  Q_CLASS      VARCHAR2(32),
  Q_LEVEL      VARCHAR2(32),
  CONTENT      VARCHAR2(2000),
  Q_KEY        VARCHAR2(50),
  RESOLVE      VARCHAR2(2000),
  EXECUTE_TIME VARCHAR2(10),
  Q_DATA       VARCHAR2(2000),
  CREATE_DATE  DATE default sysdate,
  MODIFY_DATE  DATE,
  OPERATOR     VARCHAR2(32),
  REMARK       VARCHAR2(300),
  STATUS       CHAR(1) default 0,
  SEQ          NUMBER default 0
)
;
comment on table KJK_QUESTION
  is '�μ���Ŀ��';
comment on column KJK_QUESTION.CW_ID
  is '�μ�ID';
comment on column KJK_QUESTION.Q_TYPE
  is '�������ͣ���ӦKJK_DIC��QUESTION_TYPE��';
comment on column KJK_QUESTION.Q_CLASS
  is '���ͣ���ӦKJK_DIC��QUESTION_CLASS��';
comment on column KJK_QUESTION.Q_LEVEL
  is '�����Ѷ�';
comment on column KJK_QUESTION.CONTENT
  is '���';
comment on column KJK_QUESTION.Q_KEY
  is '��';
comment on column KJK_QUESTION.RESOLVE
  is '����';
comment on column KJK_QUESTION.EXECUTE_TIME
  is '���������ʱ��';
comment on column KJK_QUESTION.Q_DATA
  is 'ѡ������';
comment on column KJK_QUESTION.CREATE_DATE
  is '����ʱ��';
comment on column KJK_QUESTION.MODIFY_DATE
  is '�޸�ʱ��';
comment on column KJK_QUESTION.OPERATOR
  is '������';
comment on column KJK_QUESTION.REMARK
  is '��ע';
comment on column KJK_QUESTION.STATUS
  is '����״̬��0 ����,1 ʧЧ��';
comment on column KJK_QUESTION.SEQ
  is '���� ����';
alter table KJK_QUESTION
  add constraint PK_KJK_QUESTION primary key (Q_ID);

prompt
prompt Creating table NCME_EXPERT_NEW
prompt ==============================
prompt
create table NCME_EXPERT_NEW
(
  EXP_ID      VARCHAR2(12) not null,
  AREA        VARCHAR2(64),
  MAJOR       VARCHAR2(64),
  EXP_NAME    VARCHAR2(64) not null,
  TITLE       VARCHAR2(64),
  UNIT        VARCHAR2(128),
  TEL         VARCHAR2(32),
  MOBILE      VARCHAR2(200),
  EMAIL       VARCHAR2(64),
  ID_CARD     VARCHAR2(18),
  BANK_NAME   VARCHAR2(128),
  BANK_CARD   VARCHAR2(36),
  ADDRESS     VARCHAR2(128),
  REMARK      VARCHAR2(4000),
  ADD_DATE    DATE,
  UPDATE_DATE DATE,
  LOG         VARCHAR2(4000)
)
;
comment on column NCME_EXPERT_NEW.EXP_ID
  is 'ID';
comment on column NCME_EXPERT_NEW.AREA
  is '����';
comment on column NCME_EXPERT_NEW.MAJOR
  is 'רҵ';
comment on column NCME_EXPERT_NEW.EXP_NAME
  is '����';
comment on column NCME_EXPERT_NEW.TITLE
  is 'ְ��/ְ��';
comment on column NCME_EXPERT_NEW.UNIT
  is '��λ';
comment on column NCME_EXPERT_NEW.TEL
  is '����';
comment on column NCME_EXPERT_NEW.MOBILE
  is '�ֻ�';
comment on column NCME_EXPERT_NEW.EMAIL
  is '����';
comment on column NCME_EXPERT_NEW.ID_CARD
  is '���֤��';
comment on column NCME_EXPERT_NEW.BANK_NAME
  is '����';
comment on column NCME_EXPERT_NEW.BANK_CARD
  is '���п���';
comment on column NCME_EXPERT_NEW.ADDRESS
  is '��ַ';
comment on column NCME_EXPERT_NEW.REMARK
  is '���';
comment on column NCME_EXPERT_NEW.ADD_DATE
  is '���ʱ��';
comment on column NCME_EXPERT_NEW.UPDATE_DATE
  is '�޸�ʱ��';
comment on column NCME_EXPERT_NEW.LOG
  is '��־';
alter table NCME_EXPERT_NEW
  add constraint NCME_EXPERT_NEW_KEY primary key (EXP_ID);

prompt
prompt Creating table NCME_SUBJECT
prompt ===========================
prompt
create table NCME_SUBJECT
(
  SUBJECT_ID    VARCHAR2(8) not null,
  SUBJECT_NAME  VARCHAR2(32) not null,
  WEIGHT        INTEGER,
  SUBJECT2_ID   VARCHAR2(2),
  SUBJECT2_NAME VARCHAR2(50),
  GUIDE         VARCHAR2(1000),
  EXP_ID        VARCHAR2(12),
  KEY_GUIDE     VARCHAR2(1000),
  PRAC_GUIDE    VARCHAR2(1000)
)
;
alter table NCME_SUBJECT
  add primary key (SUBJECT_ID);
create unique index IDX_NCME_SUBJECT on NCME_SUBJECT (SUBJECT_NAME);
create index IDX_NCME_SUBJECT_ID2 on NCME_SUBJECT (SUBJECT2_ID);

prompt
prompt Creating table USER_DISTRICT
prompt ============================
prompt
create table USER_DISTRICT
(
  DISTRICT_ID   VARCHAR2(9) not null,
  DISTRICT_NAME VARCHAR2(50) not null,
  PARENT_ID     VARCHAR2(9),
  DIALLING_CODE VARCHAR2(8),
  CARD_FLAG     INTEGER default 1
)
cache;
alter table USER_DISTRICT
  add primary key (DISTRICT_ID);

prompt
prompt Creating sequence KJK_COURSEWARE_SEQ
prompt ====================================
prompt
create sequence KJK_COURSEWARE_SEQ
minvalue 1
maxvalue 9999999999999
start with 6811912
increment by 1
cache 10
order;

prompt
prompt Creating sequence NCME_EXPERT_SEQ
prompt =================================
prompt
create sequence NCME_EXPERT_SEQ
minvalue 1
maxvalue 999999999999
start with 2000030869
increment by 1
cache 20;


spool off
