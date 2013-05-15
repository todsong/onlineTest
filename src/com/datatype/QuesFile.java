package com.datatype;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class QuesFile
{
    private int subjectId;
    private File file;
    private String contextDir;
    private String quesType;
    
    
    public int getSubjectId()
    {
        return subjectId;
    }
    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }
    public File getFile()
    {
        return file;
    }
    public void setFile(File file)
    {
        this.file = file;
    }
    public String getContextDir()
    {
        return contextDir;
    }
    public void setContextDir(String contextDir)
    {
        this.contextDir = contextDir;
    }
    public String getQuesType()
    {
        return quesType;
    }
    public void setQuesType(String quesType)
    {
        this.quesType = quesType;
    }
}
