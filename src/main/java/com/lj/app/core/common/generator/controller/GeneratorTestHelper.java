package com.lj.app.core.common.generator.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.lj.app.core.common.generator.GeneratorFacade;
import com.lj.app.core.common.generator.util.FileHelper;

import ch.qos.logback.core.util.FileUtil;


/**
 * 生成器的测试工具类,用于将模板内容生成在某个临时文件夹,
 * 然后读取临时文件夹的内容返回 返回之前会删除临时文件夹
 * 
 */
public class GeneratorTestHelper {
	private static AtomicLong count = new AtomicLong(System.currentTimeMillis());
	
	public static void generateByTable(GeneratorFacade gf, ZipOutputStream zip,
      String... tableNames) throws Exception {
    File tempDir = getOutputTempDir();
    gf.getGenerator().setOutRootDir(tempDir.getPath());
    gf.generateByTable(tableNames);
     readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding(),zip);
  }

	private static void readEntireDirectoryContentAndDelete(File tempDir,String encoding, ZipOutputStream zip) {
	  
	  try {
	     Thread.currentThread().sleep(10);
	  }catch(Exception e) {
	    e.printStackTrace();
	  }
	  
    List<File> files = FileHelper.searchAllNotIgnoreFile(tempDir);
    //获取模板列表
    for(File f : files) {
       
        try {
            //添加到zip
            if(f.isDirectory()) {
              continue;
            }else {
              zip.putNextEntry(new ZipEntry(f.getAbsolutePath() + f.getName()));
            }
            
            byte[] buf = new byte[4096];
            int len;
            
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( f ) );
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zip.write( buf, 0, len );
            }
            
            FileHelper.deleteQuietly(f);
            
            IOUtils.closeQuietly(bis);
            
            zip.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException("压缩文件异常", e);
        }
    }
    
    for(File f : files) {
        FileHelper.deleteQuietly(f);
    }
    
  }

  private static File getOutputTempDir() {
		File tempDir = new File(FileHelper.getTempDir(), "GeneratorTestHelper" + File.separator
				+ count.incrementAndGet() + ".tmp");
		tempDir.deleteOnExit();
		return tempDir;
	}
	

}