/*
This code was based on Jollama https://github.com/aholinch/jollama/tree/main
and modified by Alessandro Soro (QUT) to use in CAB302

MIT License

Copyright (c) jollama

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.example.quizCoach.ollama;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class JollamaImageUtil {
    public static String imageToBase64(String imageFile)
    {
        File f = new File(imageFile);
        return imageToBase64(f);
    }

    public static String imageToBase64(File imageFile)
    {
        int size = (int)imageFile.length();
        byte ba[] = new byte[(int)size];

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(imageFile);
            byte small[] = new byte[4096];
            int numRead = 0;
            int tot = 0;
            numRead = fis.read(small);
            while(numRead > 0)
            {
                System.arraycopy(small, 0, ba, tot, numRead);
                tot+=numRead;

                numRead = fis.read(small);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(fis != null)try {fis.close();}catch(Exception ex) {}
        }
        return bytesToBase64(ba);
    }

    public static String bytesToBase64(byte ba[])
    {
        return Base64.getEncoder().encodeToString(ba);
    }

}
