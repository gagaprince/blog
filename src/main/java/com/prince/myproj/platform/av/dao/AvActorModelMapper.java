package com.prince.myproj.platform.av.dao;

import com.prince.myproj.platform.av.models.AvActorModel;

import java.util.List;
import java.util.Map;

public interface AvActorModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    int insert(AvActorModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    int insertSelective(AvActorModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    AvActorModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AvActorModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AvActorModel record);

    List<AvActorModel> selectAll(Map<String,String> map);
    List<AvActorModel> selectByName(String name);
}