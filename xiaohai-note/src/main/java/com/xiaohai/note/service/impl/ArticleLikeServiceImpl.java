package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.common.utils.RoleUtils;
import com.xiaohai.note.dao.ArticleLikeMapper;
import com.xiaohai.note.dao.ArticleMapper;
import com.xiaohai.note.dao.NotificationsMapper;
import com.xiaohai.note.pojo.dto.ArticleLikeDto;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.entity.ArticleLike;
import com.xiaohai.note.pojo.entity.Notifications;
import com.xiaohai.note.pojo.query.ArticleLikeQuery;
import com.xiaohai.note.pojo.vo.ArticleLikeVo;
import com.xiaohai.note.pojo.vo.NotificationsVo;
import com.xiaohai.note.service.ArticleLikeService;
import com.xiaohai.note.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * 用户文章点赞表 服务实现类
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    private final NotificationsService notificationsService;

    private final NotificationsMapper notificationsMapper;

    private final ArticleMapper articleMapper;

    @Override
    public Integer add(ArticleLikeVo vo){

        if(vo.getClickLike()==1){
            ArticleLike like=new ArticleLike();
            like.setArticleId(vo.getArticleId());
            like.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
            baseMapper.insert(like);
            // 点赞消息推送
            NotificationsVo notificationsVo=new NotificationsVo();
            Article article =articleMapper.selectById(vo.getArticleId());
            notificationsVo.setUserId(article.getUserId());
            notificationsVo.setArticleId(vo.getArticleId());
            notificationsVo.setLikeId(like.getId());
            notificationsVo.setType("1");
            notificationsService.add(notificationsVo);
            return 1;
        }else{
            ArticleLike articleLike=baseMapper.selectOne(new QueryWrapper<ArticleLike>().eq("user_id",StpUtil.getLoginId()).eq("article_id",vo.getArticleId()));
            notificationsMapper.delete(new QueryWrapper<Notifications>().eq("like_id",articleLike.getId()));
            return  baseMapper.deleteById(articleLike.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            ArticleLike oldArticle = baseMapper.selectById(id);
            RoleUtils.checkActiveUserAndAdmin(oldArticle.getUserId());
            baseMapper.deleteById(id);
            notificationsMapper.delete(new QueryWrapper<Notifications>().eq("like_id",id));
        }
        return ids.length;
    }

    @Override
    public Integer updateData(ArticleLikeVo vo){
        ArticleLike articleLike=new ArticleLike();
        BeanUtils.copyProperties(vo,articleLike);
        return baseMapper.updateById(articleLike);
    }

    @Override
    public ArticleLike findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<ArticleLikeDto> findListByPage(ArticleLikeQuery query){
        Long userId = query.getUserId();
        //判断角色是否是管理员和demo
        if (RoleUtils.checkRole() && userId == null) {
            //不是管理员、demo只查询当前用户数据
            userId = Long.valueOf((String) StpUtil.getLoginId());
        }
        query.setUserId(userId);
        IPage<ArticleLikeDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<ArticleLikeDto> iPage = baseMapper.selectArticleLikePage(wherePage,query);

        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,iPage.getRecords());
    }
}
