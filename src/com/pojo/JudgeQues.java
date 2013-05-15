package com.pojo;

import java.io.Serializable;

import com.util.MD5Util;

public class JudgeQues extends AbstractQues
{
    private static final long serialVersionUID = -1735917878808886298L;
    
    public void calculateHash()
    {
        hash = MD5Util.getMD5(subjectId + qName);
    }
}
