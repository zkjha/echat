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
  strLevelsId               VARCHAR(50) NOT NULL,      -- 主键	
  strLevelsName             VARCHAR(50) NOT NULL,      -- 会员卡级别名称
  intStatus                 INT DEFAULT 1,             -- 会员卡级别状态0：禁用 1：启用
  strEmployeeId             VARCHAR(50)  NULL,         -- 操作员工ID
  strEmployeeName           VARCHAR(50)  NULL,         -- 操作员工姓名
  strEmployeeLoginName      VARCHAR(50)  NULL,         -- 操作员工登录账号
  strInsertTime             VARCHAR(50) NOT NULL,      -- 录入时间
  strUpdateTime             VARCHAR(50) DEFAULT '',    -- 修改时间
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
  STRBUYPRICE               VARCHAR(50) DEFAULT '',    -- 购买价格
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
  STRDESC                   VARCHAR(50) DEFAULT '',    -- 变更描述
  STREMPLOYEEID             VARCHAR(50) NOT NULL,      -- 操作员工ID
  STREMPLOYEEREALNAME       VARCHAR(50) NOT NULL,      -- 操作员工姓名
  STREMPLOYEELOGINNAME      VARCHAR(50) NOT NULL,      -- 操作员工登录账号
  STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
  PRIMARY KEY (STRRECORDID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX memberid_index ON tb_integral_change_record(STRMEMBERID);
CREATE INDEX membercardnum_index ON tb_integral_change_record(STRMEMBERCARDNUM);

-- ==============================================================
-- Table: tb_recharge_record                【后台售后充值记录表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_recharge_record;
CREATE TABLE tb_recharge_record
(
  strRechargeId             VARCHAR(50) NOT NULL,       -- 主键
  strMemberId               VARCHAR(50) NOT NULL,       -- 会员ID
  strMemberCardNum          VARCHAR(50) NOT NULL,       -- 会员卡号
  strMemberName             VARCHAR(50) NOT NULL,       -- 用户姓名   
  dBalance                  DECIMAL(11,2) DEFAULT 0.00, -- 金额
  strEmployeeId             VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeRealName       VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName      VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strInsertTime             VARCHAR(50) NOT NULL,       -- 录入时间
  iRechargeType             INT DEFAULT 0,              -- 充值类型 0:现金充值 1售后储值充值
  strReserved               VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strRechargeId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxRechargeRecordOnRechargeId on tb_recharge_record(strRechargeId);
create index indxRechargeRecordOnMemberCardId  on tb_recharge_record(strMemberCardNum);
create index indxRechargeRecordOnInsertTime   on tb_recharge_record(strInsertTime);

-- ==============================================================
-- Table: tb_recharge_order                 【会员现金充值订单表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_recharge_order;
CREATE TABLE tb_recharge_order
(
  STRORDERID                VARCHAR(50) NOT NULL,       -- 主键
  STRMEMBERID               VARCHAR(50) NOT NULL,       -- 会员ID
  STRMEMBERCARDNUM          VARCHAR(50) NOT NULL,       -- 会员卡号
  STRMEMBERNAME             VARCHAR(50) NOT NULL,       -- 用户姓名   
  DBALANCE                  DECIMAL(11,2) DEFAULT 0.00, -- 充值金额
  STREMPLOYEEID             VARCHAR(50) NOT NULL,       -- 操作员工ID
  STREMPLOYEEREALNAME       VARCHAR(50) NOT NULL,       -- 操作员工姓名
  STREMPLOYEELOGINNAME      VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  STRINSERTTIME             VARCHAR(50) NOT NULL,       -- 录入时间
  INTSTATUS                 INT DEFAULT 0,              -- 订单状态0：待支付 1：已支付
  STRPAYTIME                VARCHAR(50) DEFAULT '',     -- 支付时间
  INTPAYTYPE                INT DEFAULT 0,              -- 支付方式 0：现金支付 1：微信支付 2：支付宝支付
  STRTHIRDPARTYTRADEFLOW    VARCHAR(50) DEFAULT '',     -- 三方支付流水号
  PRIMARY KEY (STRORDERID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxRechargeOrderOnMemberId on tb_recharge_order(STRMEMBERID);

-- ==============================================================
-- Table: tb_goods                                      【商品信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_goods;
CREATE TABLE tb_goods
(
  strGoodsId                VARCHAR(50) NOT NULL,       -- 主键
  strGoodsBarCode           VARCHAR(50) NOT NULL,       -- 商品条形码
  strUnitId                 VARCHAR(50) NOT NULL,       -- 商品单位ID
  strUnitName               VARCHAR(50) NOT NULL,       -- 商品计量单位名称
  strGoodsName              VARCHAR(50) NOT NULL,       -- 商品名称
  
  strGoodsTypeId            VARCHAR(50) NOT NULL,       -- 商品类型ID
  strGoodsTypeName          VARCHAR(50) NOT NULL,       -- 计量单位名称
  strSupplierName           VARCHAR(50) NOT NULL,       -- 供应商名称  
  iRequiredIntegral         int DEFAULT 0,              -- 兑换商品所需积分  
  dEnterPrice               DECIMAL(11,2) DEFAULT 0.00, -- 商品进价
  
  dSalePrice                DECIMAL(11,2) DEFAULT 0.00, -- 商品销售价格
  iStock                    int DEFAULT 0,              -- 商品库存
  iPreferentialType         int DEFAULT 0,              -- 商品优惠类型 0 不优惠 1 按照会员等级优惠
  iState                    int DEFAULT 0,              -- 商品状态 0 不使用 1 使用
  
  txtGoodsDesc              text null,                  -- 富文本描述信息
  txtGoodsDescDetail        text null,                  -- 富文本详情描述信息
 
  strEmployeeId             VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeName           VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName      VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strInsertTime             VARCHAR(50) NOT NULL,       -- 录入时间
  strUpdateTime             VARCHAR(50) DEFAULT '',     -- 修改时间
  PRIMARY KEY (STRGOODSID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxGoodsOnGoodsId  on tb_goods(strGoodsId);
create index indxGoodsOnGoodsName  on tb_goods(strGoodsName);
create index indxGoodsOnInsertTime  on tb_goods(strInsertTime);

-- ==============================================================
-- Table: tb_goodspreferential_mapping         商品按会员等级优惠信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_goodspreferential_mapping;
CREATE TABLE tb_goodspreferential_mapping
(
  strPreferentialId         VARCHAR(50) NOT NULL,       -- 主键
  strGoodsId                VARCHAR(50) NOT NULL,       -- 主键
  strGoodsName              VARCHAR(50) NOT NULL,       -- 商品名称
  strLevelsId               VARCHAR(50) NOT NULL,       -- 会员等级ID	
  strLevelsName             VARCHAR(50) NOT NULL,       -- 会员卡级别名称
  iRequiredIntegral         int DEFAULT 0,              -- 兑换商品所需积分  
  strEmployeeId             VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeName           VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName      VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strInsertTime             VARCHAR(50) NOT NULL,       -- 录入时间
  strUpdateTime             VARCHAR(50) DEFAULT '',     -- 修改时间
  PRIMARY KEY (strPreferentialId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxGoodspreferentialOnGoodsId  on tb_goodspreferential_mapping(strGoodsId);
create index indxGoodspreferentialOnGoodsName  on tb_goodspreferential_mapping(strLevelsId);
create index indxGoodspreferentialOnInsertTime  on tb_goodspreferential_mapping(strInsertTime);

-- ==============================================================
-- Table: tb_goods_order                            【商品订单表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_goods_order;
CREATE TABLE tb_goods_order
(
  STRORDERID                VARCHAR(50) NOT NULL,       -- 主键
  STRMEMBERID               VARCHAR(50) NOT NULL,       -- 会员ID
  STRMEMBERCARDNUM          VARCHAR(50) NOT NULL,       -- 会员卡号
  STRMEMBERNAME             VARCHAR(50) NOT NULL,       -- 用户姓名
  STRGOODSID                VARCHAR(50) NOT NULL,       -- 商品ID
  STRGOODSNAME              VARCHAR(50) NOT NULL,       -- 商品名称
  INTCOUNT                  INT DEFAULT 0,              -- 数量
  DPRICE                    DECIMAL(11,2) DEFAULT 0.00, -- 商品单价
  DAMOUNT                   DECIMAL(11,2) DEFAULT 0.00, -- 订单总金额
  STRINSERTTIME             VARCHAR(50) NOT NULL,       -- 录入时间
  INTSTATUS                 INT DEFAULT 0,              -- 订单状态0：待支付 1：已支付 2：已发货 3：已完成
  STRPAYTIME                VARCHAR(50) DEFAULT '',     -- 支付时间
  INTPAYTYPE                INT DEFAULT 0,              -- 支付方式 0：积分兑换 1：微信支付 2：支付宝支付
  STRTHIRDPARTYTRADEFLOW    VARCHAR(50) DEFAULT '',     -- 三方支付流水号
  STREXPRESSNUMBER          VARCHAR(50) DEFAULT '',     -- 快递单号
  STREXPRESSCOMPANY         VARCHAR(50) DEFAULT '',     -- 快递公司
  PRIMARY KEY (STRORDERID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxRechargeOrderOnMemberId on tb_goods_order(STRMEMBERID);


-- ==============================================================
-- Table: tb_measurement_unit                【计量单位表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_measurement_unit;
CREATE TABLE tb_measurement_unit
(
  strUnitId             VARCHAR(50) NOT NULL,       -- 主键
  strUnitName           VARCHAR(50) NOT NULL,       -- 计量单位名称
  strUnitDesc           VARCHAR(255) NULL,       -- 计量描述
  strReserved           VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strUnitId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxMeasurementUnitOnUnitId on tb_measurement_unit(strUnitId);
create index indxMeasurementUnitOnUnitName  on tb_measurement_unit(strUnitName);


-- ==============================================================
-- Table: tb_goods_type                【商品分类】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_goods_type;
CREATE TABLE tb_goods_type
(
  strGoodsTypeId             VARCHAR(50) NOT NULL,       -- 主键
  strGoodsTypeName           VARCHAR(50) NOT NULL,       -- 商品类别名称
  strGoodsTypeDesc           VARCHAR(255) NULL,          -- 商品类别描述
  strEmployeeId              VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeName            VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName       VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strReserved           VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strGoodsTypeId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxGoodsTypeOnTypeId on tb_goods_type(strGoodsTypeId);
create index indxGoodsTypeOnTypeName  on tb_goods_type(strGoodsTypeName);


-- ==============================================================
-- Table: tb_service_type                【服务项目分类】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_service_type;
CREATE TABLE tb_service_type
(
  strServiceTypeId             VARCHAR(50) NOT NULL,       -- 主键
  strServiceTypeName           VARCHAR(50) NOT NULL,       -- 服务类别名称
  strEmployeeId                VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeName              VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName         VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strServiceTypeDesc           VARCHAR(255) NULL,          -- 服务类别描述
  strReserved                  VARCHAR(500) NULL,                 -- 预留字段
  PRIMARY KEY (strServiceTypeId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxServiceTypeOnTypeId on tb_service_type(strServiceTypeId);
create index indxServiceTypeOnTypeName  on tb_service_type(strServiceTypeName);
create index indxServiceTypeOnEmployeedId  on tb_service_type(strEmployeeId);

-- ==============================================================
-- Table: tb_service_info                【服务项目信息】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_service_info;
CREATE TABLE tb_service_info
(
  strServiceInfoId             VARCHAR(50) NOT NULL,       -- 主键
  strServiceInfoName           VARCHAR(50) NOT NULL,       -- 服务类别名称
  strServiceTypeId             VARCHAR(50) NOT NULL,       -- 主键
  strServiceTypeName           VARCHAR(50) NOT NULL,       -- 服务类别名称 
  dSalePrice                   DECIMAL(11,2) DEFAULT 0.00, -- 服务销售价格
  strServiceBarCode            VARCHAR(50) NOT NULL,        -- 编号条码
  strUnitId                    VARCHAR(50) NOT NULL,        -- 服务单位ID
  strUnitName                  VARCHAR(50) NOT NULL,        -- 服务计量单位名称 
  strSupplierName              VARCHAR(50) NOT NULL,        -- 供应商名称  
  iPreferentialType            int DEFAULT 0,               -- 商品优惠类型 0 不优惠 1 按照会员等级优惠
  iState                       int DEFAULT 0,               -- 商品状态 0 不使用 1 使用 
  txtServiceDesc               text null,                   -- 富文本描述信息
  txtServiceDescDetail         text null,                   -- 富文本详情描述信息
  strEmployeeId                VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeName              VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName         VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strInsertTime                VARCHAR(50) NOT NULL,       -- 录入时间
  strUpdateTime                VARCHAR(50) DEFAULT '',     -- 修改时间
  strReserved                  VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strServiceInfoId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxServiceInfoOnServiceId on tb_service_info(strServiceInfoId);
create index indxServiceInfoOnServiceName  on tb_service_info(strServiceInfoName);
create index indxServiceInfoOnTypeId  on tb_service_info(strServiceTypeId);
create index indxServiceInfoOnEmployeedId  on tb_service_info(strEmployeeId);

-- ==============================================================
-- Table: tb_servicepreferential_mapping         【服务按会员等级优惠信息表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_servicepreferential_mapping;
CREATE TABLE tb_servicepreferential_mapping
(
  strPreferentialId         VARCHAR(50) NOT NULL,       -- 主键
  strServiceInfoId          VARCHAR(50) NOT NULL,       -- 服务ID
  strServiceInfoName        VARCHAR(50) NOT NULL,       -- 服务名称
  strLevelsId               VARCHAR(50) NOT NULL,       -- 会员等级ID	
  strLevelsName             VARCHAR(50) NOT NULL,       -- 会员卡级别名称
  iRequiredIntegral         int DEFAULT 0,              -- 兑换商品所需积分  
  strEmployeeId             VARCHAR(50) NOT NULL,       -- 操作员工ID
  strEmployeeName           VARCHAR(50) NOT NULL,       -- 操作员工姓名
  strEmployeeLoginName      VARCHAR(50) NOT NULL,       -- 操作员工登录账号
  strInsertTime             VARCHAR(50) NOT NULL,       -- 录入时间
  strUpdateTime             VARCHAR(50) DEFAULT '',     -- 修改时间
  PRIMARY KEY (strPreferentialId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxServicepreferentialOnGoodsId  on tb_servicepreferential_mapping(strServiceInfoId);
create index indxServicepreferentialOnGoodsName  on tb_servicepreferential_mapping(strLevelsId);
create index indxServicepreferentialOnInsertTime  on tb_servicepreferential_mapping(strInsertTime);


-- ==============================================================
-- Table: tb_advertispic_info                【首页广告轮播图片信息】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_advertispic_info;
CREATE TABLE tb_advertispic_info
(
  strAdvPicId             VARCHAR(50) NOT NULL,       -- 主键
  strAdvPicName           VARCHAR(100) NOT NULL,       -- 广告图片名称
  iAdvPicWeight           int default 0,              -- 广告图片权重
  strAdvLinkPage          VARCHAR(500)    NULL,       -- 页面链接地址
  strInsertTime           VARCHAR(50) NOT NULL,       -- 录入时间
  strReserved             VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strAdvPicId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxAdvPicInfoOnPicId on tb_advertispic_info(strAdvPicId);
create index indxAdvPicInfoOnPicName on tb_advertispic_info(strAdvPicName);
create index indxAdvPicInfoOnTime on tb_advertispic_info(strInsertTime);
create index indxAdvPicInfoOnWeight on tb_advertispic_info(iAdvPicWeight);

-- ==============================================================
-- Table: tb_integralclear_rule                【积分清零】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_integralclear_rule;
CREATE TABLE tb_integralclear_rule
(
  strValidBeginTime         VARCHAR(50) NOT NULL,       -- 有效期开始时间
  strValidEndTime           VARCHAR(50) NOT NULL,       -- 有效期截止时间
  iIsValid                  int default 0,              -- 是否生效                       
  strReserved               VARCHAR(500) NULL           -- 预留字段
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxIntegralClearOnEndTime on tb_integralclear_rule(strValidEndTime);
create index indxIntegralClearOnTime on tb_integralclear_rule(strValidBeginTime,strValidEndTime);
insert into tb_integralclear_rule(strValidBeginTime,strValidEndTime,iIsValid,strReserved) values('2017-03-01 00:00:00','2099-12-21 23:59:59',0,'');


-- ==============================================================
-- Table: tb_storedticket_rule               【储值卡规则信息】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_storedticket_rule;
CREATE TABLE tb_storedticket_rule
(
  strTicketId               VARCHAR(50) NOT NULL,       -- 主键
  strTicketName             VARCHAR(50) NOT NULL,       -- 储值券名称
  iTicketType               int default 0,              -- 储值券类型  0 售后储值 1 现金储值 
  strValidEndTime           VARCHAR(50) default '',     -- 有效期截止时间  只有售后充值才有有效期 售后充值就是赠送给用户的
  iIsValid                  int default 0,              -- 是否生效 0 禁用 1 启用
  strTicketRuleDesc         VARCHAR(1024) NOT NULL,     -- 储值券使用规则描述                       
  strReserved               VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strTicketId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxStoreTicketOnEndTime on tb_storedticket_rule(strValidEndTime);
create index indxStoreTicketOnId on tb_storedticket_rule(strTicketId);
-- insert into tb_storedticket_rule(strTicketId,strTicketName,iTicketType,strValidEndTime,iIsValid,strTicketRuleDesc,strReserved)
-- values('100001','售后储值',0,'2099-03-01 00:00:00',0,'No desc','');
-- insert into tb_storedticket_rule(strTicketId,strTicketName,iTicketType,strValidEndTime,iIsValid,strTicketRuleDesc,strReserved)
-- values('100002','现金储值',1,'',0,'No desc','');
-- ==============================================================
-- Table: tb_voucherticket_rule               【抵用券规则信息】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_voucherticket_rule;
CREATE TABLE tb_voucherticket_rule
(
  strVoucherTicketId        VARCHAR(50) NOT NULL,       -- 主键
  strVoucherTicketName      VARCHAR(50) NOT NULL,       -- 储值券名称
  strValidEndTime           VARCHAR(50) NOT NULL,       -- 有效期截止时间  
  iIsValid                  int default 0,              -- 是否生效 0 禁用 1 启用
  iCanUseCount              int default 1,              -- 可使用次数
  strUseCountDesc           VARCHAR(50) default '',     -- 使用次数描述
  strRuleDesc               VARCHAR(1024)  NULL,        -- 储值券使用规则描述                       
  strReserved               VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strVoucherTicketId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxVoucherTicketOnEndTime on tb_voucherticket_rule(strValidEndTime);
create index indxVoucherTicketOnName on tb_voucherticket_rule(strVoucherTicketName);


-- ==============================================================
-- Table: tb_voucherticket_useinfo               【抵用券领用及使用信息】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_voucherticket_useinfo;
CREATE TABLE tb_voucherticket_useinfo
(
  strVoucherUseInfoId       VARCHAR(50) NOT NULL,       -- 主键
  strVoucherTicketId        VARCHAR(50) NOT NULL,       -- 抵用券ID
  strVoucherTicketName      VARCHAR(50) NOT NULL,       -- 储值券名称
  strValidEndTime           VARCHAR(50) NOT NULL,       -- 有效期截止时间  
  strMemberId               VARCHAR(50) NOT NULL,       -- 领用会员ID	
  strMemberName             VARCHAR(50) NOT NULL,       -- 领用会员姓名 
  iStat                     int default 1,              -- 抵用券状态 1未使用 2已使用 3已过期
  iCanUseCount              int default 1,              -- 可使用次数
  iUsedCount                int default 0,              -- 已经使用次数
  iIsValid                  int default 0,              -- 是否生效 0 禁用 1 启用
  strUseCountDesc           VARCHAR(50) default '',     -- 使用次数描述
  strRuleDesc               VARCHAR(1024)  NULL,        -- 储值券使用规则描述                       
  strReserved               VARCHAR(500) NULL,          -- 预留字段
  PRIMARY KEY (strVoucherUseInfoId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxVoucherTicketUseInfoOnEndTime on tb_voucherticket_useinfo(strValidEndTime);
create index indxVoucherTicketUseInfoOnId on tb_voucherticket_useinfo(strVoucherTicketId);
create index indxVoucherTicketUseInfoMemberId on tb_voucherticket_useinfo(strMemberId);
-- -----------------------签到积分规则表----------------------------------
-- 表名tb_sign_integration_rule                             【签到积分规则表】
-- -------------------------------------------------------------------
DROP TABLE  IF EXISTS tb_sign_integration_rule;
CREATE TABLE tb_sign_integration_rule
(
strSignId			VARCHAR(50)		NOT NULL,		-- 主键
strSignDays			VARCHAR(50)		NOT NULL,		-- 签到天数
strStatus			VARCHAR(5)		DEFAULT '1',	-- 签到状态:1表示连续签到 0表示非连续签到
iIntegration	 	int				not null,		-- 积分
strEnabled			VARCHAR(5)		DEFAULT '1',	-- 是否启用：1表示启用 0表示禁用
strCreationTime		varchar(50)		Not null,		-- 创建规则时间
strLastAccessedTime varchar(50)		Not Null,		-- 最后一次修改时间
strEmployeeId		varchar(50)		not null,		-- 登录员工ID
strEmployeeName		varchar(50)		not null,		-- 登录员工帐号
strEmployeeRealName varchar(50)		not null,		-- 登录员工姓名
PRIMARY KEY(strSignId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- --------------------消费积分抵现规则表------------------------------------------
-- 表名:tb_integration_cash_rule 消费积分抵现表                           【消费积分抵现规则表】
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS tb_integration_cash_rule;
CREATE TABLE tb_integration_cash_rule
(
    strId				VARCHAR(50)		NOT NULL,		-- 关键字
    iIntegration		int				not null,		-- 积分
    dCash				decimal(11,2)	Not null,		-- 可抵扣现金
    strEnabled			VARCHAR(5)		DEFAULT '1',	-- 是否启用
    strCreationTime		varchar(50)		Not null,		-- 创建规则时间
	strLastAccessedTime varchar(50)		Not Null,		-- 最后一次修改时间
	strEmployeeId		varchar(50)		not null,		-- 登录员工ID
	strEmployeeName		varchar(50)		not null,		-- 登录员工帐号
	strEmployeeRealName varchar(50)		not null,		-- 登录员工姓名
    PRIMARY KEY(strId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- -------------------------------------前端首页图片广告设置表-------------------------
-- 表名:tb_img_advertisement                          【前端首页图片广告设置】
-- --------------------------------------------------------------------------------
drop table if exists tb_img_advertisement;
create table tb_img_advertisement
(
	strImgId			varchar(50)			not null,			-- 图片ID
    strImgName			varchar(500)		not null,			-- 图片名称
	iImgOrder			int(5)				not null,			-- 图片排序
	strCreationTime		varchar(50)			not null,			-- 创建记录时间
    strLastAccessedTime	varchar(50)			not null,			-- 最后修改时间
    strEmployeeId		varchar(50)			not null,			-- 登录员工ID
    strEmployeeName		varchar(50)			not null,			-- 登录员工帐号
    strEmployeeRealName	varchar(50)			not null,			-- 登录员工真实姓名
    primary key(strImgId)
)engine=innodb default charset=utf8;
-- ----------------------------------------------------------------------------------------
-- tableName:tb_fistMemberInitiation_integrationPresents			【初次入会｜赠送积分表】
-- -----------------------------------------------------------------------------------------
drop table if exists tb_fistMemberInitiation_integrationPresents;
CREATE TABLE tb_fistMemberInitiation_integrationPresents
(
  StrIntegrationPresentsId		VARCHAR(50)		NOT NULL,				-- 关键字
  iIntegrationPresentsValue 	INT				NOT NULL DEFAULT 0,		-- 赠送积分数
  iEnabled 						INT(2) 			NOT NULL DEFAULT 0, 	-- 启用状态：1启用 0禁用,默认状态为：0
  strEmployeeId 				VARCHAR(50) 	NOT NULL,				-- 管理员ID
  strEmployeeName 				VARCHAR(50) 	NOT NULL,				-- 管理员账号
  strEmployeeRealName 			VARCHAR(50) 	NOT NULL,				-- 管理员姓名
  strCreationTime 				VARCHAR(50) 	NOT NULL,				-- 记录创建时间
  strLastAccessedTime 			VARCHAR(50) 	NOT NULL,				-- 记录修改时间
  PRIMARY KEY (StrIntegrationPresentsId)
)engine=innodb default charset=utf8;
-- -----------------------------------------------------------------------------------------
-- tableName:tb__firstMemberInitiation_storedTicketPresents			【初次入会｜赠送储值表】
-- ----------------------------------------------------------------------------------------
drop table if exists tb_firstMemberInitiation_storedTicketPresents;
CREATE TABLE tb_firstMemberInitiation_storedTicketPresents
(
  strStoredTicketPresentsId		VARCHAR(50) 	NOT NULL,	-- 关键字
  iStoredValuePresents 			INT NULL 		DEFAULT 0,	-- 赠送储值数量
  iTotalStoredTicketNum 		INT 			NOT NULL,	-- 可对所有消费者发放的储值卡总数量
  iRestStoredTicketNum 			INT NULL		NOT NULL,	-- 可对所有消费者发放的储值卡剩余数量
  strValidateBeginTime 			VARCHAR(50) 	NOT NULL,	-- 赠送储值的使用有效期
  strValidateEndTime 			VARCHAR(50) 	NOT NULL,	-- 赠送储值的使用有效期
  iEnabled 						INT(2) 			DEFAULT 0,	-- 是否启用：1启用 0禁用 默认禁用
  strEmployeeId 				VARCHAR(50) 	NOT NULL,	-- 管理员ID
  strEmployeeName 				VARCHAR(50) 	NOT NULL,	-- 管理员账号
  strEmployeeRealName 			VARCHAR(50) 	NOT NULL,	-- 管理员姓名
  strCreationTime 				VARCHAR(50) 	NOT NULL,	-- 记录创建时间
  strLastAccessedTime 			VARCHAR(50) 	NOT NULL,	-- 记录修改时间
  PRIMARY KEY (strStoredTicketPresentsId)
)engine=innodb default charset=utf8;
-- ------------------------------------------------------------------------
-- tableName:tb_voucher_ticket_presents			【初次入会｜赠送抵用卷表】
-- -----------------------------------------------------------------------
drop table if exists tb_firstMemberInitiation_VoucherTicketPresents;
CREATE TABLE tb_firstMemberInitiation_VoucherTicketPresents 
(
  strVoucherTicketPresentsId 	VARCHAR(50) 		NOT NULL,	-- 关键字
  strVoucherTicketKindId		VARCHAR(50) 		NOT null,	-- 抵用卷种类ID,关联tb_voucherticket_infomanage的ID
  iTotalVoucherTicketNum 		INT 				NOT NULL,	-- 可对所有顾客发放的抵用卷总数量
  iRestVoucherTicketNum 		INT 				NOT NULL,	-- 可对所有顾客发放的抵用卷剩余量
  iEnabled 						INT(2) 				DEFAULT 0,	-- 是否启用：1启用
  strEmployeeId 				VARCHAR(50) 		NOT NULL,	-- 管理员ID
  strEmployeeName 				VARCHAR(50) 		NOT NULL,	-- 管理员帐号
  strEmployeeRealName 			VARCHAR(50) 		NOT NULL,	-- 管理员姓名
  strCreationTime 				VARCHAR(50) 		NOT NULL,	-- 记录创建时间
  strLastAccessedTime 			VARCHAR(50) 		NOT NULL,	-- 记录修改时间
  PRIMARY KEY (strVoucherTicketPresentsId)
)engine=innodb default charset=utf8;
-- --------------------------------------------------------------------------
-- tableName:tb_member_levelsRights_mapping				【会员等级权益表】
-- --------------------------------------------------------------------------
drop table if exists tb_member_levelsRights_mapping;
create table tb_member_levelsRights_mapping
(
 strLevelsRightsMappingId	varchar(50)			not null,			-- 关键字
 strLevelsId				varchar(50)			not null,			-- 会员级别ID
 strLevelsName				varchar(50)			not null,			-- 会员级别名称
 strRightsId				varchar(50)			not null,			-- 会员相应级别对应的权益(购买商品或购买服务的)ID
 strRightsName				varchar(100)		not null,			-- 会员相应级别对应的权益名称(购买商品或购买服务的)名称
 iRightsStatus				int(2)				default 0,			-- 权益状态 0 表示购买商品 1表示购买服务
 dDiscount					decimal(5,2),		default 0.00		-- 折扣率
 iPreferentialTimes			int(5)				default 0,			-- 优惠次数
 strEmployeeId				varchar(50)			not null,			-- 登录员工ID
 strEmployeeName			varchar(50)			not null,			-- 登录员工帐号
 strEmployeeRealName		varchar(50)			not null,			-- 登录员工姓名 
 strCreationTime			varchar(50)			not null,			-- 创建记录时间
 strLastAccessedTime		varchar(50)			not null,			-- 修改时间
 primary key(strLevelsRightsMappingId)
) engine=innodb default charset=utf8;
-- ==============================================================
-- Table: tb_voucherticket_infomanage               【抵用券信息管理】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_voucherticket_infomanage;
CREATE TABLE tb_voucherticket_infomanage
(
  strVoucherTicketId        VARCHAR(50) 	NOT NULL,       -- 主键
  strVoucherTicketName      VARCHAR(50) 	NOT NULL,       -- 抵用券名
  dVoucherTicketAmount    DECIMAL(11,2) 	DEFAULT 0.00, 	-- 抵用券金额
  strValidEndTime           VARCHAR(50) 	NOT NULL,       -- 有效期截止时间  
  iIsValid                  int default 0,              	-- 是否生效 0 禁用 1 启用
  strRuleDesc               VARCHAR(1024) 	NULL,        	-- 抵用券使用规则描述                       
  strReserved               VARCHAR(500) 	NULL,         	-- 预留字段
  strEmployeeId 		    VARCHAR(50) 	NOT NULL,		-- 管理员ID
  strEmployeeName 			VARCHAR(50) 	NOT NULL,		-- 管理员账号
  strEmployeeRealName 		VARCHAR(50) 	NOT NULL,		-- 管理员姓名
  strCreationTime 			VARCHAR(50) 	NOT NULL,		-- 记录创建时间
  strLastAccessedTime 		VARCHAR(50) 	NOT NULL,		-- 记录修改时间
  PRIMARY KEY (strVoucherTicketId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxVoucherTicketOnEndTime on tb_voucherticket_infomanage(strValidEndTime);
create index indxVoucherTicketOnName on tb_voucherticket_infomanage(strVoucherTicketName);
-- ------------------------------------------------------------------------------------------------
-- tableName:tb_activity												活动｜【活动表】
-- -------------------------------------------------------------------------------------------------
drop table if exists tb_rechargePresents_activity;
create table tb_rechargePresents_activity
(
 strActivityId			varchar(50)			not null,		-- 关键字
 strActivityName		varchar(50)			not null,		-- 活动名称
 strLevelsId			varchar(50)			not null,		-- 会员级别id	关联tb_member_level id
 strActivityBeginTime	varchar(50)			not null,		-- 活动开始时间
 strActivityEndTime		varchar(50)			not null,		-- 活动结束时间
 iActivityKinds			int(2)				not null,		-- 活动类型:0自定义赠送型，1消费赠送型，2充值 赠送型
 strEmployeeId 		    VARCHAR(50) 		NOT NULL,		-- 管理员ID
 strEmployeeName 		VARCHAR(50) 		NOT NULL,		-- 管理员账号
 strEmployeeRealName 	VARCHAR(50) 		NOT NULL,		-- 管理员姓名
 strCreationTime 		VARCHAR(50) 		NOT NULL,		-- 记录创建时间
 strLastAccessedTime 	VARCHAR(50) 		NOT NULL,		-- 记录修改时间
 primary key(strActivityId)
) engine=innodb default charset=utf8;
-- ----------------------------------------------------------------------------------------
-- tableName:tb_rechargePresents_integration					活动｜【充值送积分】
-- ----------------------------------------------------------------------------------------
drop table if exists tb_rechargePresents_integration;
create table tb_rechargePresents_integration
(
strPresentsIntegrationId					varchar(50)			not null,		-- 关键字
strActivityId								varchar(50)			not null,		-- 活动ID 关联 tb_activity id
dPerTimeRechargeAmount						decimal(11,2)		not null,		-- 充值金额{每充值多少金额可获赠1积分｝
dLeastRechargeAmount						decimal(11,2)		not null,		-- 最少充值多少钱才有资格获得积分
iEnabled									int(2)				default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   						VARCHAR(50) 		NOT NULL,		-- 管理员ID
strEmployeeName 							VARCHAR(50) 		NOT NULL,		-- 管理员账号
strEmployeeRealName 						VARCHAR(50) 		NOT NULL,		-- 管理员姓名
strCreationTime 							VARCHAR(50) 		NOT NULL,		-- 记录创建时间
strLastAccessedTime 						VARCHAR(50) 		NOT NULL,		-- 记录修改时间
primary key(strPresentsIntegrationId)
) engine=innodb default charset=utf8;
-- ---------------------------------------------------------------------------------------------
-- tableName:tb_rechargeStoredTicketPresents_storedValue			活动|【充储值卡送储值】
-- -----------------------------------------------------------------------------------------------
drop table if exists tb_rechargePresents_storedValue;
create table tb_rechargePresents_storedValue
(
strPresentsStoredValueId			varchar(50)			not null,		-- 主键字
strActivityId						varchar(50)			not null,		-- 活动ID 关联 tb_activity id
dRechargeAmount						decimal(11,2)		not null,		-- 现金充值金额
dPresentsAmount						decimal(11,2)		not null,		-- 赠送储值量
strValidateBeginTime				varchar(50),						-- 有效期开始时间  暂不使用该属性
strValidateEndTime					varchar(50),							-- 有效期截止时间 暂不使用该属性
iEnabled							int(2)				default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   				VARCHAR(50) 		NOT NULL,		-- 管理员ID
strEmployeeName 					VARCHAR(50) 		NOT NULL,		-- 管理员账号
strEmployeeRealName 				VARCHAR(50) 		NOT NULL,		-- 管理员姓名
strCreationTime 					VARCHAR(50) 		NOT NULL,		-- 记录创建时间
strLastAccessedTime 				VARCHAR(50) 		NOT NULL,		-- 记录修改时间
primary key(strPresentsStoredValueId)
) engine=innodb default charset=utf8;
-- -------------------------------------------------------------------------------------------------
-- tableName:tb_rechargePresents_voucher							活动｜【充值送抵用券】
-- --------------------------------------------------------------------------------------------------
drop table if exists tb_rechargePresents_voucher;
create table tb_rechargePresents_voucher
(
strRechargePresentsVoucherId		varchar(50)		not null,		-- 关键字
strBasePresentsVoucherTicketId      VARCHAR(50) 	NOT NULL,       -- 基本赠送抵用券ID 
strMorePresentsVoucherTicketId      VARCHAR(50) 	NOT NULL,		-- 多赠送抵用券ID
strActivityId						varchar(50)		not null,		-- 活动ID 关联 tb_activity id
dMinimumRechargeAmount				decimal(11,2)	not null,		-- 充值多少钱可以领一张抵用券
iMinimumPresentsVoucherNumber		int				not null,		-- 赠送张数
dMoreRechargeAmount					decimal(11,2)	default 0,		-- 每多充值多少钱
iMorePresentsVoucherNumber			int				default 0,		-- 多赠送多少钱
iEnabled							int(2)			default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   				VARCHAR(50) 	NOT NULL,		-- 管理员ID
strEmployeeName 					VARCHAR(50) 	NOT NULL,		-- 管理员账号
strEmployeeRealName 				VARCHAR(50) 	NOT NULL,		-- 管理员姓名
strCreationTime 					VARCHAR(50) 	NOT NULL,		-- 记录创建时间
strLastAccessedTime 				VARCHAR(50) 	NOT NULL,		-- 记录修改时间
primary key(strRechargePresentsVoucherId)
) engine=innodb default charset=utf8;
-- --------------------------------------------------自定义赠送 活动信息表------------------------
-- tableName:tb_userDefinedPresents_Activity					【自定义赠送 活动信息表】
-- 结构同充值赠送活动表 tb_rechargePresents_activity
-- ---------------------------------------------------------------------------------
drop table if exists tb_userDefinedPresents_Activity;
create table tb_userDefinedPresents_Activity
(
 strActivityId			varchar(50)			not null,		-- 关键字
 strActivityName		varchar(50)			not null,		-- 活动名称
 strLevelsId			varchar(50)			not null,		-- 会员级别id	关联tb_member_level id
 strActivityBeginTime	varchar(50)			not null,		-- 活动开始时间
 strActivityEndTime		varchar(50)			not null,		-- 活动结束时间
 iActivityKinds			int(2)				not null,		-- 活动类型:0自定义赠送型，1消费赠送型，2充值 赠送型
 strEmployeeId 		    VARCHAR(50) 		NOT NULL,		-- 管理员ID
 strEmployeeName 		VARCHAR(50) 		NOT NULL,		-- 管理员账号
 strEmployeeRealName 	VARCHAR(50) 		NOT NULL,		-- 管理员姓名
 strCreationTime 		VARCHAR(50) 		NOT NULL,		-- 记录创建时间
 strLastAccessedTime 	VARCHAR(50) 		NOT NULL,		-- 记录修改时间
 primary key(strActivityId)
) engine=innodb default charset=utf8;
-- ------------------------------------------------------------------------------------
-- tableName:tb_userDefinedPresents_Integration					【自定义活动赠送积分表】
-- -------------------------------------------------------------------------------------------
drop table if exists tb_userDefinedPresents_Integration;
create table tb_userDefinedPresents_Integration
(
strPresentsIntegrationId					varchar(50)			not null,		-- 关键字
strActivityId								varchar(50)			not null,		-- 活动ID 关联 tb_activity id
iPresentsIntegration						int					not null,		-- 获得积分数量
iEnabled									int(2)				default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   						VARCHAR(50) 		NOT NULL,		-- 管理员ID
strEmployeeName 							VARCHAR(50) 		NOT NULL,		-- 管理员账号
strEmployeeRealName 						VARCHAR(50) 		NOT NULL,		-- 管理员姓名
strCreationTime 							VARCHAR(50) 		NOT NULL,		-- 记录创建时间
strLastAccessedTime 						VARCHAR(50) 		NOT NULL,		-- 记录修改时间
primary key(strPresentsIntegrationId)
) engine=innodb default charset=utf8;
-- --------------------------------------------------------------------------------------------------
-- tableName:tb_userDefinedPresents_storedValue			【自定义活动赠送储值】
-- --------------------------------------------------------------------------------------------
drop table if exists tb_userDefinedPresents_storedValue;
create table tb_userDefinedPresents_storedValue
(
strPresentsStoredValueId			varchar(50)			not null,		-- 主键字
strActivityId						varchar(50)			not null,		-- 活动ID 关联 tb_activity id
dPresentsAmount						decimal(11,2)		not null,		-- 赠送储值量
iEnabled							int(2)				default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   				VARCHAR(50) 		NOT NULL,		-- 管理员ID
strEmployeeName 					VARCHAR(50) 		NOT NULL,		-- 管理员账号
strEmployeeRealName 				VARCHAR(50) 		NOT NULL,		-- 管理员姓名
strCreationTime 					VARCHAR(50) 		NOT NULL,		-- 记录创建时间
strLastAccessedTime 				VARCHAR(50) 		NOT NULL,		-- 记录修改时间
primary key(strPresentsStoredValueId)
) engine=innodb default charset=utf8;
-- --------------------------------------------------------------------------------------------------------
-- tableName:tb_userDefinedPresents_voucher					【自定义赠送抵用券】
-- ---------------------------------------------------------------------------------------------
drop table if exists tb_userDefinedPresents_voucher;
create table tb_userDefinedPresents_voucher
(
strPresentsVoucherId				varchar(50)		not null,		-- 关键字
strVoucherTicketId        			VARCHAR(50) 	NOT NULL,       -- 抵用券ID关联 tb_voucherticket_infomanage  id
strActivityId						varchar(50)		not null,		-- 活动ID 关联 tb_activity id
iTotalNum							int				not null,		-- 对所有可赠送会员的总张数
iRestNum							int 			not null,		-- 还剩的张数
iEnabled							int(2)			default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   				VARCHAR(50) 	NOT NULL,		-- 管理员ID
strEmployeeName 					VARCHAR(50) 	NOT NULL,		-- 管理员账号
strEmployeeRealName 				VARCHAR(50) 	NOT NULL,		-- 管理员姓名
strCreationTime 					VARCHAR(50) 	NOT NULL,		-- 记录创建时间
strLastAccessedTime 				VARCHAR(50) 	NOT NULL,		-- 记录修改时间
primary key(strPresentsVoucherId)
) engine=innodb default charset=utf8;
-- --------------------------------------------------------------------------
-- tableName:tb_userDefinedPresents_Activity					【消费增送 活动信息表】
-- 结构同充值赠送活动表 tb_rechargePresents_activity
-- ---------------------------------------------------------------------------------
drop table if exists tb_consumePresents_Activity;
create table tb_consumePresents_Activity
(
 strActivityId			varchar(50)			not null,		-- 关键字
 strActivityName		varchar(50)			not null,		-- 活动名称
 strLevelsId			varchar(50)			not null,		-- 会员级别id	关联tb_member_level id
 strActivityBeginTime	varchar(50)			not null,		-- 活动开始时间
 strActivityEndTime		varchar(50)			not null,		-- 活动结束时间
 iActivityKinds			int(2)				not null,		-- 活动类型:0现金储值消费，1线上现金消费
 strIsCumulation		varchar(2)			not null,		-- 单笔消费是否累积赠送 ，0不累积 1 累积
 strEmployeeId 		    VARCHAR(50) 		NOT NULL,		-- 管理员ID
 strEmployeeName 		VARCHAR(50) 		NOT NULL,		-- 管理员账号
 strEmployeeRealName 	VARCHAR(50) 		NOT NULL,		-- 管理员姓名
 strCreationTime 		VARCHAR(50) 		NOT NULL,		-- 记录创建时间
 strLastAccessedTime 	VARCHAR(50) 		NOT NULL,		-- 记录修改时间
 primary key(strActivityId)
) engine=innodb default charset=utf8;
-- -------------------------------------------------------------------------------------------
-- tableName:tb_consumePresents_integration						【消费赠送积分】
-- -----------------------------------------------------------------------------------------
drop table if exists tb_consumePresents_integration;
create table tb_consumePresents_integration
(
strIntegrationId							varchar(50)			not null,		-- 关键字
strActivityId								varchar(50)			not null,		-- 活动ID 关联 tb_activity id
dConsumeCashAmount							decimal(11,2)		not null,		-- 消费额
iPresentsIntegrationAmount					int					default 0,		-- 赠送积分数量
iEnabled									int(2)				default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   						VARCHAR(50) 		NOT NULL,		-- 管理员ID
strEmployeeName 							VARCHAR(50) 		NOT NULL,		-- 管理员账号
strEmployeeRealName 						VARCHAR(50) 		NOT NULL,		-- 管理员姓名
strCreationTime 							VARCHAR(50) 		NOT NULL,		-- 记录创建时间
strLastAccessedTime 						VARCHAR(50) 		NOT NULL,		-- 记录修改时间
primary key(strIntegrationId)
) engine=innodb default charset=utf8;
-- -------------------------------------------------------------------------------------------------
-- tableName:tb_consumePresents_storedValue							【消费赠送储值】
-- -----------------------------------------------------------------------------------------------
drop table if exists tb_consumePresents_storedValue;
create table tb_consumePresents_storedValue
(
strStoredTicketId							varchar(50)			not null,		-- 关键字
strActivityId								varchar(50)			not null,		-- 活动ID 关联 tb_activity id
dConsumeCashAmount							decimal(11,2)		not null,		-- 消费额
iPresentsIntegrationAmount					int					default 0,		-- 赠送积分数量
iEnabled									int(2)				default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   						VARCHAR(50) 		NOT NULL,		-- 管理员ID
strEmployeeName 							VARCHAR(50) 		NOT NULL,		-- 管理员账号
strEmployeeRealName 						VARCHAR(50) 		NOT NULL,		-- 管理员姓名
strCreationTime 							VARCHAR(50) 		NOT NULL,		-- 记录创建时间
strLastAccessedTime 						VARCHAR(50) 		NOT NULL,		-- 记录修改时间
primary key(strStoredTicketId)
) engine=innodb default charset=utf8;
-- --------------------------------------------------------------------------------------------------------
-- tableName:tb_consumePresents_voucher					【消费赠送抵用券】
-- ---------------------------------------------------------------------------------------------
drop table if exists tb_consumePresents_voucher;
create table tb_consumePresents_voucher
(
strConsumePresentsVoucherId			varchar(50)		not null,		-- 关键字
strVoucherTicketId        			VARCHAR(50) 	NOT NULL,       -- 抵用券ID关联 tb_voucherticket_infomanage  id
strActivityId						varchar(50)		not null,		-- 活动ID 关联 tb_activity id
dConsumeCashAmount					decimal(11,2)	default 0.00,   -- 消费金额
iPresentsIntegrationAmount			int				default 0,		-- 赠送积分数量
iEnabled							int(2)			default 0,		-- 启用状态：1启用，0禁用
strEmployeeId 		   				VARCHAR(50) 	NOT NULL,		-- 管理员ID
strEmployeeName 					VARCHAR(50) 	NOT NULL,		-- 管理员账号
strEmployeeRealName 				VARCHAR(50) 	NOT NULL,		-- 管理员姓名
strCreationTime 					VARCHAR(50) 	NOT NULL,		-- 记录创建时间
strLastAccessedTime 				VARCHAR(50) 	NOT NULL,		-- 记录修改时间
primary key(strConsumePresentsVoucherId)
) engine=innodb default charset=utf8;
-- ==============================================================
-- Table: tb_memberPurchase_order                            【收银台｜会员订单表】                          
-- ==============================================================
DROP TABLE IF EXISTS tb_memberPurchase_order;
CREATE TABLE tb_memberPurchase_order
(
  strOrderId                VARCHAR(50) NOT NULL,       -- 主键订单号
  strMemberId               VARCHAR(50) NOT NULL,       -- 会员ID
  strMemberCardNumber		varchar(50) not null,		-- 会员卡编号
  strMemberName             VARCHAR(50) NOT NULL,       -- 用户姓名
  strLevelsId				VARCHAR(50) NOT NULL,       -- 会员级别ID
  strProductServiceId       VARCHAR(50) NOT NULL,       -- 商品或服务ID
  strProductServiceName     VARCHAR(50) NOT NULL,       -- 商品或服务名称
  iPurchaseType				int default 0,				-- 购买的类型：0商品 1服务
  iPurchaseAmount           int DEFAULT 0,              -- 购买商品或服务数量
  strUnitName               VARCHAR(50) NOT NULL,       -- 商品或服务计量单位名称
  dPrice                    DECIMAL(11,2) DEFAULT 0.00, -- 商品或服务单价(原价）
  dPurchaseCashTotalAmount  DECIMAL(11,2) DEFAULT 0.00, -- 订单总金额（原价计算得来)
  dPreferentialPrice		DECIMAL(11,2) DEFAULT 0.00, -- 商品或服务的会员优惠价
  dPreferentialCashTotalAmount	DECIMAL(11,2) DEFAULT 0.00, -- 优惠后的商品或服务总价
  iStatus                 	int DEFAULT 0,              -- 订单状态0：待支付 1：已支付 2：已发货 3：已完成
  iPayStandard				int default 0,				-- 支付标准：0 会员价（优惠价)支付,1原价支付
  strPayTime                VARCHAR(50) DEFAULT '',     -- 支付时间
  iPayType                	int DEFAULT 0,              -- 支付方式 0：积分兑换 1：微信支付 2：支付宝支付
  strThirdPartyTradeFlow    VARCHAR(50) DEFAULT '',     -- 三方支付流水号
  strExpressNumber          VARCHAR(50) DEFAULT '',     -- 快递单号
  strExpressCompany         VARCHAR(50) DEFAULT '',     -- 快递公司
  strComment				VARCHAR(200) DEFAULT '',     -- 备注字段
  strEmployeeId 		    VARCHAR(50) 	NOT NULL,		-- 管理员ID
  strEmployeeName 		    VARCHAR(50) 	NOT NULL,		-- 管理员账号
  strEmployeeRealName 	    VARCHAR(50) 	NOT NULL,		-- 管理员姓名
  strCreationTime 			VARCHAR(50) 	NOT NULL,		-- 记录创建时间
  strLastAccessedTime 		VARCHAR(50) 	NOT NULL,		-- 记录修改时间
  PRIMARY KEY (strOrderId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create index indxMemberOrderIdOnMemberOrder on tb_memberPurchase_order(strOrderId);