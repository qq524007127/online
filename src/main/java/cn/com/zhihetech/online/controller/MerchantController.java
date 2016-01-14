package cn.com.zhihetech.online.controller;

import cn.com.zhihetech.online.bean.Admin;
import cn.com.zhihetech.online.bean.Merchant;
import cn.com.zhihetech.online.bean.Role;
import cn.com.zhihetech.online.commons.Constant;
import cn.com.zhihetech.online.commons.ResponseMessage;
import cn.com.zhihetech.online.dao.IShopShowDao;
import cn.com.zhihetech.online.service.*;
import cn.com.zhihetech.util.common.StringUtils;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.Order;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by ShenYunjie on 2015/11/20.
 */
@Controller
public class MerchantController extends SupportController {

    private final String REGIST_MERCHANT = "registerMerchant";

    @Resource(name = "merchantService")
    private IMerchantService merchantService;

    @Resource(name = "imgInfoService")
    private IImgInfoService imgInfoService;

    @Resource(name = "roleService")
    private IRoleService roleService;

    @Resource(name = "adminService")
    private IAdminService adminService;

    @Resource(name = "shopShowService")
    private IShopShowService shopShowService;

    @RequestMapping("admin/merchant")
    public String merchantPage() {
        return "admin/merchant";
    }

    @RequestMapping(value = "admin/merchantInfoPage")
    public ModelAndView infoPage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("admin/merchantInfoPage");
        Admin admin = this.getCurrentAdmin(request);
        Merchant merchant = this.merchantService.getById(admin.getMerchant().getMerchantId());
        modelAndView.addObject("merchant", merchant);
        return modelAndView;
    }

    @RequestMapping("admin/examinMerchant")
    public ModelAndView examinMerchantPage() {
        ModelAndView modelAndView = new ModelAndView("admin/merchantExamin");
        List<Role> roles = this.roleService.getAllByParams(null, null);
        modelAndView.addObject("roleList", roles);
        return modelAndView;
    }

    @RequestMapping(value = "admin/merchant/info/{id}")
    public ModelAndView merchantInfo(@PathVariable(value = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("admin/merchantInfo");
        Merchant merchant = this.merchantService.getById(id);
        modelAndView.addObject("merchant", merchant);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/list")
    public PageData<Merchant> getMerchantPageData(HttpServletRequest request) {
        IQueryParams queryParams = createQueryParams(request);
        if (!queryParams.sortContainsKey("permit")) {
            queryParams.sort("permit", Order.DESC);
        }
        if (!queryParams.sortContainsKey("merchOrder")) {
            queryParams.sort("merchOrder", Order.ASC);
        }
        return this.merchantService.getPageData(createPager(request), queryParams);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/add", method = RequestMethod.POST)
    public ResponseMessage addMerchant(Merchant merchant) {
        merchant.setCreateDate(new Date());
        this.merchantService.add(merchant);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/edit", method = RequestMethod.POST)
    public ResponseMessage editMerchant(Merchant merchant) {
        this.merchantService.update(merchant);
        return executeResult();
    }

    @RequestMapping(value = "admin/updateImg")
    public ModelAndView updateImgPage(HttpServletRequest request) {
        Merchant merchant = this.merchantService.getById(getCurrentAdminId(request));
        ModelAndView modelAndView = new ModelAndView("admin/merchantImgEdit");

        modelAndView.addObject("merchant",merchant);

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/updateCoverImg", method = RequestMethod.POST)
    public ResponseMessage updateCoverImg(Merchant merchant) {
        this.merchantService.updateCoverImg(merchant);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/updateHeaderImg",method = RequestMethod.POST)
    public ResponseMessage updateHeaderImg(Merchant merchant){
        this.merchantService.updateHeadImg(merchant);
        return executeResult();
    }
    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/examinList")
    public PageData<Merchant> getExaminMerchant(HttpServletRequest request) {
        IQueryParams queryParams = this.createQueryParams(request);
        queryParams.andEqual("examinState", Constant.EXAMINE_STATE_SUBMITED);
        return this.merchantService.getPageData(this.createPager(request), queryParams);
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/updateExaminStateOk")
    public ResponseMessage updateExaminStateOk(Merchant merchant, Admin admin, String[] roleIds) {
        if (roleIds != null) {
            for (String id : roleIds) {
                if (StringUtils.isEmpty(id)) {
                    continue;
                }
                Role role = new Role();
                role.setRoleId(id);
                admin.getRoles().add(role);
            }
        }
        this.merchantService.updateExaminStateOk(merchant, admin);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/updatePermit")
    public ResponseMessage updatePermit(String merchantId, boolean permit) {
        this.merchantService.updatePermit(merchantId, permit);
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/updateExaminStateDissmiss")
    public ResponseMessage updateExaminStateDissmiss(String merchantId, String examinMsg) {
        this.merchantService.updateExaminStateDissmiss(merchantId, examinMsg);
        return executeResult();
    }

    @RequestMapping(value = "admin/api/merchant/editMerchantRegisterInfo")
    public ModelAndView editMerchantInfo(HttpServletRequest request) {
        Admin admin = this.getCurrentAdmin(request);
        Merchant merchant = this.merchantService.getById(admin.getMerchant().getMerchantId());
        ModelAndView modelAndView = new ModelAndView("admin/merchantRegisterInfoEdit");
        modelAndView.addObject("merchant", merchant);
        request.getSession().setAttribute("merchant", merchant);
        return modelAndView;
    }

    @RequestMapping(value = "admin/api/merchant/editMerchantRegisterInfoForm", method = RequestMethod.POST)
    public String merchantData(Merchant merchant, HttpServletRequest request) {
        request.getSession().setAttribute(REGIST_MERCHANT, merchant);
        return "redirect:/admin/api/merchant/redirectEditMerchantRegisterUpload";
    }

    @RequestMapping(value = "admin/api/merchant/redirectEditMerchantRegisterUpload")
    public String imgInfoForm(HttpServletRequest request) {
        if (request.getSession().getAttribute(REGIST_MERCHANT) != null) {
            return "admin/merchantRegisterUploadEdit";
        }
        return "redirect:/admin/api/merchant/editMerchantRegisterInfo";
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/editMerchantRegisterUploadForm", method = RequestMethod.POST)
    public ResponseMessage submitMerchantInfo(HttpServletRequest request, Merchant merchantImg) {
        if (request.getSession().getAttribute(REGIST_MERCHANT) != null) {
            Merchant merchant = (Merchant) request.getSession().getAttribute(REGIST_MERCHANT);
            merchant.setCoverImg(merchantImg.getCoverImg());
            merchant.setHeaderImg(merchantImg.getHeaderImg());
            merchant.setOpraterIDPhoto(merchantImg.getOpraterIDPhoto());
            merchant.setOrgPhoto(merchantImg.getOrgPhoto());
            merchant.setBusLicePhoto(merchantImg.getBusLicePhoto());
            merchant.setApplyLetterPhoto(merchantImg.getApplyLetterPhoto());
            this.merchantService.update(merchant);
        }
        return executeResult();
    }

    @ResponseBody
    @RequestMapping(value = "admin/api/merchant/editMerchantInfoForm")
    public ResponseMessage editMerchantInfoForm(Merchant merchant, HttpServletRequest request) {
        Merchant _merchant = (Merchant) request.getSession().getAttribute("merchant");
        _merchant.setMerchName(merchant.getMerchName());
        _merchant.setMerchTell(merchant.getMerchTell());
        _merchant.setAddress(merchant.getAddress());
        _merchant.setAlipayCode(merchant.getAlipayCode());
        _merchant.setOrgCode(merchant.getOrgCode());
        _merchant.setLicenseCode(merchant.getLicenseCode());
        _merchant.setTaxRegCode(merchant.getTaxRegCode());
        _merchant.setBusinScope(merchant.getBusinScope());
        _merchant.setEmplyCount(merchant.getEmplyCount());
        _merchant.setContactID(merchant.getContactID());
        _merchant.setContactMobileNO(merchant.getContactMobileNO());
        _merchant.setContactName(merchant.getContactName());
        _merchant.setContactEmail(merchant.getContactEmail());
        _merchant.setContactPartAndPositon(merchant.getContactPartAndPositon());
        _merchant.setMerchantDetails(merchant.getMerchantDetails());
        this.merchantService.update(_merchant);
        return executeResult();
    }

}
