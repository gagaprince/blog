package com.prince.myproj.platform.av.dao;

import com.prince.myproj.platform.av.models.AvCateCodeModel;

public interface AvCateCodeModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_cate_code
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_cate_code
     *
     * @mbggenerated
     */
    int insert(AvCateCodeModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_cate_code
     *
     * @mbggenerated
     */
    int insertSelective(AvCateCodeModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_cate_code
     *
     * @mbggenerated
     */
    AvCateCodeModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_cate_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AvCateCodeModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_cate_code
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AvCateCodeModel record);
}