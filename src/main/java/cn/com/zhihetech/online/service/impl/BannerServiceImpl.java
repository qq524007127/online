package cn.com.zhihetech.online.service.impl;

import cn.com.zhihetech.online.bean.Banner;
import cn.com.zhihetech.online.bean.ImgInfo;
import cn.com.zhihetech.online.dao.IBannerDao;
import cn.com.zhihetech.online.dao.IImgInfoDao;
import cn.com.zhihetech.online.service.IBannerService;
import cn.com.zhihetech.util.hibernate.IQueryParams;
import cn.com.zhihetech.util.hibernate.commons.PageData;
import cn.com.zhihetech.util.hibernate.commons.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
@Service("bannerService")
public class BannerServiceImpl implements IBannerService {

    @Resource(name = "bannerDao")
    private IBannerDao iBannerDao;

    @Resource(name = "imgInfoDao")
    private IImgInfoDao imgInfoDao;

    @Override
    public Banner getById(String id) {
        return this.iBannerDao.findEntityById(id);
    }

    @Override
    public void delete(Banner banner) {
        this.iBannerDao.deleteEntity(banner);
    }

    @Override
    public void deleteBannerAndImg(String bannerId, String imgInfoId) {
        Banner banner = this.iBannerDao.findEntityById(bannerId);
        ImgInfo imgInfo = this.imgInfoDao.findEntityById(imgInfoId);
        this.imgInfoDao.deleteEntity(imgInfo);
        this.iBannerDao.deleteEntity(banner);
    }

    @Override
    public void delete(String bannerId) {
        Banner banner = new Banner();
        banner.setBannerId(bannerId);
        this.iBannerDao.deleteEntity(banner);
    }

    @Override
    public Banner add(Banner banner) {
        return this.iBannerDao.saveEntity(banner);
    }

    @Override
    public void update(Banner banner) {
        this.iBannerDao.updateEntity(banner);
    }

    @Override
    public List<Banner> getAllByParams(Pager pager, IQueryParams queryParams) {
        return this.iBannerDao.getEntities(pager, queryParams);
    }

    @Override
    public PageData<Banner> getPageData(Pager pager, IQueryParams iQueryParams) {
        return this.iBannerDao.getPageData(pager, iQueryParams);
    }
}
