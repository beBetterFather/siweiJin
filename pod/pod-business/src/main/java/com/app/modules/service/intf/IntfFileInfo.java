package com.app.modules.service.intf;

import com.app.modules.entity.FileInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntfFileInfo {

    List<FileInfo> queryAll();
}
