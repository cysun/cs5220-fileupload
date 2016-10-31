package cs520.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/FileUploadHandler")
public class FileUploadHandler extends javax.servlet.http.HttpServlet implements
    javax.servlet.Servlet {

    private static final long serialVersionUID = 1L;

    protected void doPost( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload( factory );

        // locate the uploaded file
        FileItem fileItem = null;
        try
        {
            for( Object o : upload.parseRequest( request ) )
            {
                if( !((FileItem) o).isFormField() )
                {
                    fileItem = (FileItem) o;
                    break;
                }
            }
        }
        catch( FileUploadException e )
        {

            throw new ServletException( e );
        }

        // display a message if no file is uploaded
        if( fileItem == null || fileItem.getSize() == 0 )
        {
            response.setContentType( "text/plain" );
            response.getWriter().println( "Please upload a file." );
        }
        // display the uploaded file
        else
        {
            response.setContentType( fileItem.getContentType() );
            InputStream in = fileItem.getInputStream();
            OutputStream out = response.getOutputStream();
            byte buffer[] = new byte[2048];
            int bytesRead;
            while( (bytesRead = in.read( buffer )) > 0 )
                out.write( buffer, 0, bytesRead );
        }
    }

}
