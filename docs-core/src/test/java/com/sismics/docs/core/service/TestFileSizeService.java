package com.sismics.docs.core.service;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.FileDao;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.event.FileDeletedAsyncEvent;
import com.sismics.docs.core.listener.async.FileDeletedAsyncListener;
import com.sismics.docs.core.model.jpa.File;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.TransactionUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class TestFileSizeService extends BaseTransactionalTest {

    @Test
    public void processFileTest() throws Exception {
        User user = createUser("processFileTest");

        FileDao fileDao = new FileDao();
        File file = createFile(user, File.UNKNOWN_SIZE);
        FileSizeService fileSizeService = new FileSizeService();
        fileSizeService.processFile(file);
        Assert.assertEquals(fileDao.getFile(file.getId()).getSize(), Long.valueOf(FILE_JPG_SIZE));
    }


//    @Test
//    public void processFileWithKnownSizeTest() throws Exception {
//        User user = createUser("processFileWithKnownSizeTest");
//
//        FileDao fileDao = new FileDao();
//        File file = createFile(user, FILE_JPG_SIZE);  // 文件大小已知
//        FileSizeService fileSizeService = new FileSizeService();
//
//        fileSizeService.processFile(file);
//        Assert.assertEquals(fileDao.getFile(file.getId()).getSize(), Long.valueOf(FILE_JPG_SIZE));
//    }
//
//    @Test
//    public void processFileWithExceptionTest() throws Exception {
//        User user = createUser("processFileWithExceptionTest");
//
//        FileSizeService fileSizeService = new FileSizeService();
//
//        try {
//            fileSizeService.processFile(null);  // 传入 null，模拟异常情况
//            Assert.fail("Expected an exception to be thrown");
//        } catch (Exception e) {
//            Assert.assertTrue(e instanceof NullPointerException);
//        }
//    }
}

