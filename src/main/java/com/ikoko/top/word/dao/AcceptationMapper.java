package com.ikoko.top.word.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.word.entity.Acceptation;
import com.ikoko.top.word.entity.AcceptationExample;

public interface AcceptationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int countByExample(AcceptationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int deleteByExample(AcceptationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int insert(Acceptation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int insertSelective(Acceptation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    List<Acceptation> selectByExample(AcceptationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    Acceptation selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int updateByExampleSelective(@Param("record") Acceptation record, @Param("example") AcceptationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int updateByExample(@Param("record") Acceptation record, @Param("example") AcceptationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int updateByPrimaryKeySelective(Acceptation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acceptation
     *
     * @mbggenerated Thu Dec 01 18:25:16 CST 2016
     */
    int updateByPrimaryKey(Acceptation record);
}