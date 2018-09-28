package com.lj.app.core.common.generator.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.lj.app.core.common.generator.Generator;
import com.lj.app.core.common.generator.GeneratorFacade;
import com.lj.app.core.common.generator.util.FileHelper;
import com.lj.app.core.common.generator.util.StringHelper;


/**
 * 生成器的测试工具类,用于将模板内容生成在某个临时文件夹,
 * 然后读取临时文件夹的内容返回 返回之前会删除临时文件夹
 * 
 */
public class GeneratorTestHelper {
	private static AtomicLong count = new AtomicLong(System.currentTimeMillis());

	public static String generateByAllTable(GeneratorFacade gf)
			throws Exception {
		File tempDir = getOutputTempDir();
		gf.getGenerator().setOutRootDir(tempDir.getPath());
		gf.generateByAllTable();
		return readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding());
	}

	public static String generateByTable(GeneratorFacade gf,
			String... tableNames) throws Exception {
		File tempDir = getOutputTempDir();
		gf.getGenerator().setOutRootDir(tempDir.getPath());
		gf.generateByTable(tableNames);
		return readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding());
	}
	
	public static void generateByTable(GeneratorFacade gf, ZipOutputStream zip,
      String... tableNames) throws Exception {
    File tempDir = getOutputTempDir();
    gf.getGenerator().setOutRootDir(tempDir.getPath());
    gf.generateByTable(tableNames);
     readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding(),zip);
  }

	public static String generateBy(Generator g, Map templateModel)
			throws Exception {
		return generateBy(g, templateModel,templateModel);
	}

	public static String generateBy(Generator g, Map templateModel, Map filePathModel)
			throws Exception {
		File tempDir = getOutputTempDir();
		g.setOutRootDir(tempDir.getPath());
		g.generateBy(templateModel, filePathModel);
		return readEntireDirectoryContentAndDelete(tempDir,g.getOutputEncoding());
	}

	private static String readEntireDirectoryContentAndDelete(File tempDir,String encoding) {
		String result = FileHelper.readEntireDirectoryContent(tempDir,encoding);
		List<File> files = FileHelper.searchAllNotIgnoreFile(tempDir);
		for(File f : files) {
			if(f.isDirectory()) continue;
			String relativePath = FileHelper.getRelativePath(tempDir,f).replace('\\', '/');
			if(StringHelper.isBlank(relativePath)) continue;
			
			result = result + "\n"+"file:"+relativePath;
		}
		try {
			FileHelper.deleteDirectory(tempDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static void readEntireDirectoryContentAndDelete(File tempDir,String encoding, ZipOutputStream zip) {
    String result = FileHelper.readEntireDirectoryContent(tempDir,encoding);
    List<File> files = FileHelper.searchAllNotIgnoreFile(tempDir);
    for(File f : files) {
      if(f.isDirectory()) continue;
      String relativePath = FileHelper.getRelativePath(tempDir,f).replace('\\', '/');
      if(StringHelper.isBlank(relativePath)) continue;
      
      result = result + "\n"+"file:"+relativePath;
    }
    
    //获取模板列表
    for(File f : files) {
        if(f.isDirectory()) continue;
        try {
            //添加到zip
            zip.putNextEntry(new ZipEntry(f.getName()));
            zip.closeEntry();
            
            FileHelper.deleteDirectory(tempDir);
            
        } catch (IOException e) {
            throw new RuntimeException("压缩文件异常", e);
        }
        
    }
    
  }

	private static File getOutputTempDir() {
		File tempDir = new File(FileHelper.getTempDir(), "GeneratorTestHelper/"
				+ count.incrementAndGet() + ".tmp");
		tempDir.deleteOnExit();
		return tempDir;
	}
	

}