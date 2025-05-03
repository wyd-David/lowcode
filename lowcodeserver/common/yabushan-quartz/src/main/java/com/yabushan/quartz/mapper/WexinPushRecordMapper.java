package com.yabushan.quartz.mapper;

import com.yabushan.quartz.domain.WexinPushRecord;

import java.util.List;

public interface WexinPushRecordMapper {

    public int insert(WexinPushRecord wexinPushRecord);

    public List<WexinPushRecord> selectList(WexinPushRecord wexinPushRecord);

    public WexinPushRecord selectLastRecord();
}
