package com.app.modules.service;

import com.app.modules.dao.FileInfoDAO;
import com.app.modules.entity.FileInfo;
import com.app.modules.service.intf.IntfFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileInfoImpl implements IntfFileInfo {

    @Autowired
    private FileInfoDAO dao;

    @Override
    public List<FileInfo> queryAll() {
        return dao.findAll();
    }
}
