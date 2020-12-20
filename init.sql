create database ShopMgr;
use ShopMgr;
create table tb_category
(
    categoryId   varchar(10)  not null
        primary key,
    categoryName varchar(10)  not null,
    description  varchar(100) null
);

create table tb_supplier
(
    supplierId   varchar(10)  not null
        primary key,
    supplierName varchar(20)  not null,
    address      varchar(100) null,
    phone        char(11)     null
);

create table tb_good
(
    goodId        varchar(10)                                 not null
        primary key,
    goodName      varchar(20)                                 not null,
    categoryId    varchar(10)                                 null,
    retailPrice   decimal(10, 2)             default 0.00     not null,
    purchasePrice decimal(10, 2)             default 0.00     not null,
    inventory     int                        default 0        not null,
    supplierId    varchar(10)                                 null,
    status        enum ('ENABLE', 'DISABLE') default 'ENABLE' not null,
    description   varchar(100)                                null,
    constraint tb_good_tb_category_categoryId_fk
        foreign key (categoryId) references tb_category (categoryId),
    constraint tb_good_tb_supplier_supplierId_fk
        foreign key (supplierId) references tb_supplier (supplierId)
);

create table tb_user
(
    userAccount varchar(11)                                                                                              not null
        primary key,
    userName    varchar(20)                                                                                           not null,
    passwd      char(32)                                                                                              not null,
    age         int                                                                             default 0             not null,
    gender      enum ('UNKNOWN', 'MALE', 'FEMALE', 'FEMALE_TO_MALE', 'MALE_TO_FEMALE', 'OTHER') default 'UNKNOWN'     not null,
    userRole    enum ('ADMINISTRATOR', 'EMPLOYEE')                                              default 'EMPLOYEE'    not null,
    status      enum ('ENABLE', 'DISABLE', 'UNACTIVATED')                                       default 'UNACTIVATED' not null,
    email       varchar(30)                                                                                           not null,
    activeCode  varchar(50)                                                                                           null
);

create table tb_document
(
    documentId  varchar(14)                                   not null
        primary key,
    price       decimal(10, 2) default 0.00                   not null,
    discount    decimal(10, 2) default 0.00                   not null,
    orderTime   datetime       default CURRENT_TIMESTAMP      not null,
    userAccount varchar(11)                                      not null,
    type        enum ('SALE', 'PURCHASE', 'IMPORT', 'EXPORT') not null,
    note        varchar(100)                                  null,
    constraint tb_document_tb_user_userAccount_fk
        foreign key (userAccount) references tb_user (userAccount)
);

create table tb_document_detail
(
    documentId varchar(14)                 not null,
    goodId     varchar(10)                 not null,
    amount     int                         not null,
    price      decimal(10, 2) default 0.00 not null,
    primary key (documentId, goodId),
    constraint tb_document_detail_tb_document_documentId_fk
        foreign key (documentId) references tb_document (documentId),
    constraint tb_document_detail_tb_good_goodId_fk
        foreign key (goodId) references tb_good (goodId)
);

insert into tb_user values ('18383132610','admin','250bfb3714e9a56043822d7efe0dced2',20,'MALE','ADMINISTRATOR','ENABLE','1353011752@qq.com',null);
