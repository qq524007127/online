package cn.com.zhihetech.online.bean;

import cn.com.zhihetech.online.commons.Constant;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by YangDaiChun on 2015/11/12.
 */
@Entity
@Table(name = "t_merchant")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Merchant extends SerializableAndCloneable {

    private String merchantId;
    private ImgInfo coverImg;   //商家封面图片
    private ImgInfo headerImg;  //商家主页顶部图片
    private District district;  //商家所在商圈
    private String merchName;
    private String merchTell;   //企业联系电话
    private String address; //详细地址
    private int longitude;  //商家定位经度
    private int latitude;   //商家定位纬度
    private String alipayCode;  //支付宝账号
    private String wxpayCode;   //微信账号
    private int merchOrder; //商家排列顺序
    private boolean permit; //是否有效
    private Date createDate;    //入驻时间
    private String orgCode; //组织机构代码，必填
    private String licenseCode; //工商执照注册码
    private String taxRegCode;  //税务登记证号
    private String businScope;  //经营范围
    private int emplyCount;  //企业规模（员工人数）
    private boolean kunmingFlag = false; //是否是昆明的商家private boolean kunmingFlag = false; //是否是昆明的商家
    private String merchantDetails;
    private Set<GoodsAttributeSet> categories;  //经营商品类别

    /*运营者（联系人）相关*/
    private String contactName; //企业联系人姓名
    private String contactPartAndPositon;   //联系人部门与职位
    private String contactMobileNO;   //联系人手机号码(必须为手机号码)
    private String contactEmail;   //联系人电子邮箱
    private String contactID;   //联系人身份证号

    /*提交材料相关*/
    private ImgInfo opraterIDPhoto;   //运营者手持身份证照片
    private ImgInfo orgPhoto;   //组织机构代码证原件照片
    private ImgInfo busLicePhoto;   //工商营业执照原件照片
    private ImgInfo applyLetterPhoto;   //加盖公章的申请认证公函（与商家纠纷事件裁定等）照片

    /*审核情况*/
    private int examinState = Constant.EXAMINE_STATE_NOT_SUBMIT;    //审核状态（默认为未提交审核）
    private String examinMsg;   //审核信息
    private String invitcode;   //受邀请码

    /*商品有关*/
    private List<Goods> recommendGoodses;  //推荐商品
    private long goodsNum; //该商家有多少商品
    private boolean isActivating;  //该商家现在是否有活动正在进行


    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid2")
    @GeneratedValue(generator = "systemUUID")
    @Column(name = "merch_id", length = 36)
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @ManyToOne
    @JoinColumn(name = "cover_img")
    public ImgInfo getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(ImgInfo coverImg) {
        this.coverImg = coverImg;
    }

    @ManyToOne
    @JoinColumn(name = "hearder_img")
    public ImgInfo getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(ImgInfo headerImg) {
        this.headerImg = headerImg;
    }

    @ManyToOne
    @JoinColumn(name = "district_id")
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Column(name = "merch_name", length = 100, nullable = false)
    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    @Column(name = "merch_tell", length = 20, nullable = false)
    public String getMerchTell() {
        return merchTell;
    }

    public void setMerchTell(String merchTell) {
        this.merchTell = merchTell;
    }

    @Column(name = "merch_address", length = 300, nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "longitude")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Column(name = "alipay_code", length = 100)
    public String getAlipayCode() {
        return alipayCode;
    }

    public void setAlipayCode(String alipayCode) {
        this.alipayCode = alipayCode;
    }

    @Column(name = "wxpay_code", length = 100)
    public String getWxpayCode() {
        return wxpayCode;
    }

    public void setWxpayCode(String wxpayCode) {
        this.wxpayCode = wxpayCode;
    }

    @Column(name = "merch_order")
    public int getMerchOrder() {
        return merchOrder;
    }

    public void setMerchOrder(int merchOrder) {
        this.merchOrder = merchOrder;
    }

    @Column(name = "permit")
    public boolean isPermit() {
        return permit;
    }

    public void setPermit(boolean permit) {
        this.permit = permit;
    }

    @JSONField(format = Constant.DEFAULT_DATE_TIME_FORMAT)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "examin_state")
    public int getExaminState() {
        return examinState;
    }

    public void setExaminState(int examinState) {
        this.examinState = examinState;
    }

    @Column(name = "org_code", length = 50)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "licen_code", length = 100)
    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    @Column(name = "tax_reg_code", length = 100)
    public String getTaxRegCode() {
        return taxRegCode;
    }

    public void setTaxRegCode(String taxRegCode) {
        this.taxRegCode = taxRegCode;
    }

    @Column(name = "bus_scope", length = 500)
    public String getBusinScope() {
        return businScope;
    }

    public void setBusinScope(String businScope) {
        this.businScope = businScope;
    }

    @Column(name = "emp_count")
    public int getEmplyCount() {
        return emplyCount;
    }

    public void setEmplyCount(int emplyCount) {
        this.emplyCount = emplyCount;
    }

    @Column(name = "contact_name", length = 50, nullable = false)
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Column(name = "part_position", length = 100, nullable = false)
    public String getContactPartAndPositon() {
        return contactPartAndPositon;
    }

    public void setContactPartAndPositon(String contactPartAndPositon) {
        this.contactPartAndPositon = contactPartAndPositon;
    }

    @Column(name = "mobile_no", length = 20, nullable = false)
    public String getContactMobileNO() {
        return contactMobileNO;
    }

    public void setContactMobileNO(String contactMobileNO) {
        this.contactMobileNO = contactMobileNO;
    }

    @Column(name = "email", length = 50, nullable = false)
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Column(name = "id_no", length = 20, nullable = false)
    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    @ManyToOne
    @JoinColumn(name = "op_id_photo")
    public ImgInfo getOpraterIDPhoto() {
        return opraterIDPhoto;
    }

    public void setOpraterIDPhoto(ImgInfo opraterIDPhoto) {
        this.opraterIDPhoto = opraterIDPhoto;
    }

    @ManyToOne
    @JoinColumn(name = "org_photo")
    public ImgInfo getOrgPhoto() {
        return orgPhoto;
    }

    public void setOrgPhoto(ImgInfo orgPhoto) {
        this.orgPhoto = orgPhoto;
    }

    @ManyToOne
    @JoinColumn(name = "bus_lice_photo")
    public ImgInfo getBusLicePhoto() {
        return busLicePhoto;
    }

    public void setBusLicePhoto(ImgInfo busLicePhoto) {
        this.busLicePhoto = busLicePhoto;
    }

    @ManyToOne
    @JoinColumn(name = "aplley_letter_photo")
    public ImgInfo getApplyLetterPhoto() {
        return applyLetterPhoto;
    }

    public void setApplyLetterPhoto(ImgInfo applyLetterPhoto) {
        this.applyLetterPhoto = applyLetterPhoto;
    }

    @Column(name = "invit_code", length = 50)
    public String getInvitcode() {
        return invitcode;
    }

    public void setInvitcode(String invitcode) {
        this.invitcode = invitcode;
    }

    @Column(name = "examin_msg", length = 500)
    public String getExaminMsg() {
        return examinMsg;
    }

    public void setExaminMsg(String examinMsg) {
        this.examinMsg = examinMsg;
    }

    @Transient
    public List<Goods> getRecommendGoodses() {
        return recommendGoodses;
    }

    public void setRecommendGoodses(List<Goods> recommendGoodses) {
        this.recommendGoodses = recommendGoodses;
    }

    @Transient
    public long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(long goodsNum) {
        this.goodsNum = goodsNum;
    }

    @Transient
    public boolean getIsActivating() {
        return isActivating;
    }

    public void setIsActivating(boolean isActivating) {
        this.isActivating = isActivating;
    }

    @Column(name = "is_kunming_merchant")
    public boolean isKunmingFlag() {
        return kunmingFlag;
    }

    public void setKunmingFlag(boolean kunmingFlag) {
        this.kunmingFlag = kunmingFlag;
    }

    @JSONField(serialize = false)
    @ManyToMany
    @JoinTable(name = "merchant_category", joinColumns = {@JoinColumn(name = "merchant_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "category_id", nullable = false)})
    public Set<GoodsAttributeSet> getCategories() {
        return categories;
    }

    public void setCategories(Set<GoodsAttributeSet> categories) {
        this.categories = categories;
    }

    @Column(name = "details")
    public String getMerchantDetails() {
        return merchantDetails;
    }

    public void setMerchantDetails(String merchantDetails) {
        this.merchantDetails = merchantDetails;
    }
}
