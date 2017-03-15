-- ==============================================================
-- Table: tb_tokenuser                  【接口访问权限用户信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_tokenuser;
CREATE TABLE tb_tokenuser
(
  STRID                     VARCHAR(50)  NOT NULL,     -- 主键	
  STRAPPID                  VARCHAR(25)  NOT NULL,     -- 账号
  STRSECRET                 VARCHAR(25)  NOT NULL,     -- 密码	
  PRIMARY KEY (STRID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO tb_tokenuser VALUES ('1', 'ecard', '123456');

-- ==============================================================
-- Table: tb_token_privilege                【接口路径访问权限表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_token_privilege;
CREATE TABLE tb_token_privilege 
(
  STRID                     VARCHAR(50) NOT NULL,      -- 主键	
  STRPRIVILEGEDESC          VARCHAR(50) NOT NULL,      -- 接口描述	
  STRPRIVILEGEURL           VARCHAR(255) NOT NULL,     -- 接口访问路径	
  PRIMARY KEY (STRID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_tokenuser_privilege  【接口访问用户与接口权限关系表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_tokenuser_privilege;
CREATE TABLE tb_tokenuser_privilege
(
  STRID                     VARCHAR(50) NOT NULL,      -- 主键	
  STRTOKENUSERID            VARCHAR(50) NOT NULL,      -- 用户ID
  STRPRIVILEGEID            VARCHAR(50) NOT NULL,      -- 接口权限ID
  PRIMARY KEY (STRID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX tokenuserid_index ON tb_tokenuser_privilege(STRTOKENUSERID);
CREATE INDEX privilegeid_index ON tb_tokenuser_privilege(STRPRIVILEGEID);

-- ==============================================================
-- Table: tb_merchant                               【商家信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_merchant;
CREATE TABLE tb_merchant
(
  STRMERCHANTID             VARCHAR(50) NOT NULL,      -- 主键
  STRMERCHANTNAME           VARCHAR(50) DEFAULT '',    -- 商家名称
  STRMERCHANTADDRESS        VARCHAR(255) DEFAULT '',   -- 商家地址
  STRMERCHANTLOGO           VARCHAR(100) DEFAULT '',   -- 商家LOGO
  STRSYSTEMVERSION          VARCHAR(50) NOT NULL,      -- 系统版本
  INTVALIDDAYS              INT DEFAULT 0,             -- 有效期天数
  INTMEMBERCOUNT            INT DEFAULT 0,             -- 会员最大允许数量
  STRSYSTEMSECRET           VARCHAR(50) NOT NULL,      -- 系统信息秘钥
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRMERCHANTID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_duty                                   【职务信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_duty;
CREATE TABLE tb_duty
(
  STRDUTYID                 VARCHAR(50) NOT NULL,      -- 主键	
  STRDUTYNAME               VARCHAR(50) NOT NULL,      -- 职务名称
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRDUTYID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_privilege                              【权限信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_privilege;
CREATE TABLE tb_privilege
(
  STRPRIVILEGEID            VARCHAR(50) NOT NULL,      -- 主键
  STRPRIVILEGENAME          VARCHAR(50) NOT NULL,      -- 权限名称
  STRTOPMENUNAME            VARCHAR(50) NOT NULL,      -- 头部菜单名称
  STRPRIVILEGEURL           VARCHAR(255) NOT NULL,     -- 权限访问路径 （顶级权限为#）
  STRPARENTID               VARCHAR(50) NOT NULL,      -- 父级权限ID (顶级权限为top)
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRPRIVILEGEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_duty_privilege                 【职务权限信息关系表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_duty_privilege;
CREATE TABLE tb_duty_privilege
(
  STRRELATIONID             VARCHAR(50) NOT NULL,      -- 主键	
  STRPRIVILEGEID            VARCHAR(50) NOT NULL,      -- 权限ID
  STRDUTYID                 VARCHAR(50) NOT NULL,      -- 职务ID
  PRIMARY KEY (STRRELATIONID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX privilegeid_index ON tb_duty_privilege(STRPRIVILEGEID);
CREATE INDEX dutyid_index ON tb_duty_privilege(STRDUTYID);

-- ==============================================================
-- Table: tb_employee                               【员工信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_employee;
CREATE TABLE tb_employee
(
  STREMPLOYEEID             VARCHAR(50) NOT NULL,      -- 主键	
  STRLOGINNAME              VARCHAR(50) NOT NULL,      -- 登录管理后台账户名
  STRPASSWORD               VARCHAR(50) NOT NULL,      -- 登录管理后台密码
  STRHEADPORTRAIT           VARCHAR(100) DEFAULT '',   -- 员工头像
  STRREALNAME               VARCHAR(50) NOT NULL,      -- 员工真实姓名
  STRMOBILE                 VARCHAR(50) NOT NULL,      -- 员工手机号码
  INTSTATUS                 INT DEFAULT 1,             -- 员工状态0：禁用 1：激活
  STRDUTYID                 VARCHAR(50) NOT NULL,      -- 职务ID
  STRMERCHANTID             VARCHAR(50) NOT NULL,      -- 商家ID
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STREMPLOYEEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_wechant_config                     【微信配置信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_wechant_config;
CREATE TABLE tb_wechant_config
(
  STRCONFIGID               VARCHAR(50) NOT NULL,      -- 主键	
  STRAPPID                  VARCHAR(50) NOT NULL,      -- 公众号ID
  STRAPPNAME                VARCHAR(50) NOT NULL,      -- 公众号账号
  STRURL                    VARCHAR(50) NOT NULL,      -- URL
  STRTOKEN                  VARCHAR(50) NOT NULL,      -- TOKEN
  INTAUTHORIZATIONSTATUS    INT DEFAULT 0,             -- 授权状态1：已授权 0：未授权
  STRENCODINGAESKEY         VARCHAR(50) NOT NULL,      -- Key
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  PRIMARY KEY (STRCONFIGID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_front_information          【前端注册页面资料信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_front_information;
CREATE TABLE tb_front_information
(
  STRINFORMATIONID          VARCHAR(50) NOT NULL,      -- 主键	
  STRCONTENT                TEXT NOT NULL,             -- 内容
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  PRIMARY KEY (STRINFORMATIONID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_member_articles                    【会员章程信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_member_articles;
CREATE TABLE tb_member_articles
(
  STRARTICLESID             VARCHAR(50) NOT NULL,      -- 主键	
  STRCONTENT                TEXT NOT NULL,             -- 内容
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  PRIMARY KEY (STRARTICLESID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_member_expand_information      【会员拓展资料信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_member_expand_information;
CREATE TABLE tb_member_expand_information
(
  STRINFORMATIONID          VARCHAR(50) NOT NULL,      -- 主键	
  STRINFORMATIONNAME        VARCHAR(50) NOT NULL,      -- 拓展资料名称
  INTISMUST                 INT DEFAULT 0,             -- 拓展资料是否必填0：否 1：是
  INTTYPE                   INT DEFAULT 0,             -- 拓展资料类型0；input 1：select
  STROPTIONS                VARCHAR(1024) DEFAULT '',  -- 选项数据,逗号隔开
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRINFORMATIONID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_member_levels                      【会员级别信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_member_levels;
CREATE TABLE tb_member_levels
(
  STRLEVELSID               VARCHAR(50) NOT NULL,      -- 主键	
  STRLEVELSNAME             VARCHAR(50) NOT NULL,      -- 会员卡级别名称
  INTSTATUS                 INT DEFAULT 1,             -- 会员卡级别状态0：禁用 1：启用
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRLEVELSID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==============================================================
-- Table: tb_member                             【会员资料信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_member;
CREATE TABLE tb_member
(
  STRMEMBERID               VARCHAR(50) NOT NULL,      -- 主键	
  STRREALNAME               VARCHAR(50) NOT NULL,      -- 姓名
  STRIDCARD                 VARCHAR(50) NOT NULL,      -- 身份证号码
  STRMOBILE                 VARCHAR(50) NOT NULL,      -- 手机号
  INTAGE                    INT DEFAULT 0,             -- 年龄
  STRMEMBERCARDNUM          VARCHAR(50) NOT NULL,      -- 会员卡号
  STRLEVELSID               VARCHAR(50) NOT NULL,      -- 会员级别ID
  INTSEX                    INT DEFAULT 0,             -- 性别0：男 1：女
  INTSTATUS                 INT DEFAULT 1,             -- 会员卡状态0：禁用 1：激活
  INTINTEGRAL               INT DEFAULT 0,             -- 会员卡积分
  DBALANCE                  DECIMAL(11,2) DEFAULT 0.00,-- 会员卡余额
  DAFTERSTOREDBALANCE       DECIMAL(11,2) DEFAULT 0.00,-- 会员卡售后储值余额
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRMEMBERID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX levelsid_index ON tb_member(STRLEVELSID);

-- ==============================================================
-- Table: tb_member_detail                  【会员详细资料信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_member_detail;
CREATE TABLE tb_member_detail
(
  STRMEMBERDETAILID         VARCHAR(50) NOT NULL,      -- 主键
  STRMEMBERID               VARCHAR(50) NOT NULL,      -- 会员ID
  STRMODELOFCAR             VARCHAR(50) NOT NULL,      -- 车辆型号
  STRCARLICENSE             VARCHAR(50) NOT NULL,      -- 车牌号码
  STRCARCOLOR               VARCHAR(50) DEFAULT '',    -- 颜色
  STRCARTYPE                VARCHAR(50) NOT NULL,      -- 车辆类型
  DBUYPRICE                 DECIMAL(11,2) DEFAULT 0.00,-- 购买价格
  STRBUYDATE                VARCHAR(50) DEFAULT '',    -- 购买日期
  STRADDRESS                VARCHAR(100) NOT NULL,     -- 住址
  STRUSENATURE              VARCHAR(50) NOT NULL,      -- 使用性质
  STRREGISTERDATE           VARCHAR(50) DEFAULT '',    -- 注册日期
  STRCARIDENTIFYCODE        VARCHAR(100) NOT NULL,     -- 车辆识别代码
  STRDATEOFISSUE            VARCHAR(50) DEFAULT '',    -- 发证日期
  STRENGINENUMBER           VARCHAR(100) NOT NULL,     -- 发动机号码
  STRPOSTPROVINCE           VARCHAR(50) DEFAULT '',    -- 邮寄地址省
  STRPOSTCITY               VARCHAR(50) DEFAULT '',    -- 邮寄地址市
  STRPOSTAREA               VARCHAR(50) DEFAULT '',    -- 邮寄地址区
  STRPOSTSTREET             VARCHAR(50) DEFAULT '',    -- 邮寄地址街道
  STRPOSTDETAILADDRESS      VARCHAR(100) DEFAULT '',   -- 邮寄地址详细信息
  STRPOSTCODE               VARCHAR(50) DEFAULT '',    -- 邮寄地址邮政编码
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  STRUPDATETIME             VARCHAR(50) DEFAULT '',    -- 修改时间
  PRIMARY KEY (STRMEMBERDETAILID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX memberid_index ON tb_member_detail(STRMEMBERID);

-- ==============================================================
-- Table: tb_member_expand_attribute        【会员拓展资料属性表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_member_expand_attribute;
CREATE TABLE tb_member_expand_attribute
(
  STRATTRIBUTEID            VARCHAR(50) NOT NULL,      -- 主键
  STRMEMBERID               VARCHAR(50) NOT NULL,      -- 会员ID
  STRINFORMATIONID          VARCHAR(50) NOT NULL,      -- 拓展资料ID
  STRATTRIBUTEVALUE         VARCHAR(50) NOT NULL,      -- 拓展资料属性值
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  PRIMARY KEY (STRATTRIBUTEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX memberid_index ON tb_member_expand_attribute(STRMEMBERID);

-- ==============================================================
-- Table: tb_integral_change_record     【会员积分手动变更记录表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_integral_change_record;
CREATE TABLE tb_integral_change_record
(
  STRRECORDID               VARCHAR(50) NOT NULL,      -- 主键
  STRMEMBERID               VARCHAR(50) NOT NULL,      -- 会员ID
  STRMEMBERCARDNUM          VARCHAR(50) NOT NULL,      -- 会员卡号
  STRMEMBERNAME             VARCHAR(50) NOT NULL,      -- 会员姓名
  INTINTEGRAL               INT DEFAULT 0,             -- 变更积分值
  STREMPLOYEEID             VARCHAR(50) NOT NULL,      -- 操作员工ID
  STREMPLOYEEREALNAME       VARCHAR(50) NOT NULL,      -- 操作员工姓名
  STREMPLOYEELOGINNAME      VARCHAR(50) NOT NULL,      -- 操作员工登录账号
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  PRIMARY KEY (STRRECORDID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX memberid_index ON tb_integral_change_record(STRMEMBERID);
CREATE INDEX membercardnum_index ON tb_integral_change_record(STRMEMBERCARDNUM);


























