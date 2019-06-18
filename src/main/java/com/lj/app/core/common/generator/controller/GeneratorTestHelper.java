package com.lj.app.core.common.generator.controller;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipOutputStream;

import com.lj.app.core.common.generator.GeneratorFacade;
import com.lj.app.core.common.generator.GeneratorProductAndConsumerFacade;
import com.lj.app.core.common.generator.TableViewData;
import com.lj.app.core.common.generator.util.FileHelper;
import com.lj.app.core.common.generator.util.ZipUtils;


/**
 * 生成器工具
 * 
 */
public class GeneratorTestHelper {
	private static AtomicLong count = new AtomicLong(System.currentTimeMillis());
	
	public static void generateByTable(GeneratorFacade gf, ZipOutputStream zip,
	        String... tableNames) throws Exception {
	      File tempDir = getOutputTempDir();
	      gf.getGenerator().setOutRootDir(tempDir.getPath());
	      gf.generateByTable(tableNames);
	      // readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding(),zip);
	       ZipUtils.doCompress(tempDir, zip);
	    }
	
	public static void generateByTable(File tempDir,GeneratorFacade gf, ZipOutputStream zip,
      String... tableNames) throws Exception {
    gf.getGenerator().setOutRootDir(tempDir.getPath());
    gf.generateByTable(tableNames);
    // readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding(),zip);
     ZipUtils.doCompress(tempDir, zip);
  }
	
	public static void generateByTable(GeneratorProductAndConsumerFacade gf, ZipOutputStream zip,
	        String... tableNames) throws Exception {
	      File tempDir = getOutputTempDir();
	      gf.getGenerator().setOutRootDir(tempDir.getPath());
	      gf.generateByTable(tableNames);
	      // readEntireDirectoryContentAndDelete(tempDir,gf.getGenerator().getOutputEncoding(),zip);
	       ZipUtils.doCompress(tempDir, zip);
	    }
	
	public static void generateByTable(File tempDir,GeneratorProductAndConsumerFacade gf, ZipOutputStream zip,
	        TableViewData tableViewData) throws Exception {
        gf.getGenerator().setOutRootDir(tempDir.getPath());
        gf.generateByTable(tableViewData);
        // ZipUtils.doCompress(tempDir, zip);
      }

  public static File getOutputTempDir() {
		File tempDir = new File(FileHelper.getTempDir(), "GeneratorTestHelper" + File.separator
				+ count.incrementAndGet() + ".tmp");
		tempDir.deleteOnExit();
		return tempDir;
	}
	

}