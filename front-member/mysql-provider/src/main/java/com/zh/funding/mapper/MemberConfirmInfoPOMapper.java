package com.zh.funding.mapper;

import com.zh.funding.frontentity.po.MemberConfirmInfoPO;
import com.zh.funding.frontentity.po.MemberConfirmInfoPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberConfirmInfoPOMapper {
    int countByExample(MemberConfirmInfoPOExample example);

    int deleteByExample(MemberConfirmInfoPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberConfirmInfoPO record);

    int insertSelective(MemberConfirmInfoPO record);

    List<MemberConfirmInfoPO> selectByExample(MemberConfirmInfoPOExample example);

    MemberConfirmInfoPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberConfirmInfoPO record, @Param("example") MemberConfirmInfoPOExample example);

    int updateByExample(@Param("record") MemberConfirmInfoPO record, @Param("example") MemberConfirmInfoPOExample example);

    int updateByPrimaryKeySelective(MemberConfirmInfoPO record);

    int updateByPrimaryKey(MemberConfirmInfoPO record);
}