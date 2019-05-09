package com.app.modules.dao;

import com.app.modules.entity.FileInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileInfoDAO继承基类
 */
public interface FileInfoDAO {

    List<FileInfo> findAll();
}