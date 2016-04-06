/*!
* Copyright 2002 - 2016 Webdetails, a Pentaho company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/
package pt.webdetails.basic.plugin;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IParameterProvider;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.data.simple.SimpleRepositoryFileData;
import org.pentaho.platform.engine.services.solution.BaseContentGenerator;
import org.pentaho.platform.util.messages.LocaleHelper;
import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

public class BasicPluginContentGenerator extends BaseContentGenerator {

  protected static Log logger = LogFactory.getLog( BasicPluginContentGenerator.class );
  private static final String PATH_PARAMETER_ID = "path";

  private IBasicPluginSettings settings;
  private IUnifiedRepository repository;

  private boolean editMode; // set to true for 'perspective.edit' action

  public BasicPluginContentGenerator( IBasicPluginSettings settings, IUnifiedRepository repository ){
    setSettings( settings );
    setRepository( repository );
  }

  @Override public Log getLogger() {
    return logger;
  }

  @Override public void createContent() throws Exception {
    info( "createContent() called in " + ( isEditMode() ? "'edit'" : "'open'" ) + " mode" );

    IParameterProvider pathParams = parameterProviders.get( PATH_PARAMETER_ID );

    String urlEncodedFilePath;

    if( pathParams != null
        && !StringUtils.isEmpty( ( urlEncodedFilePath = pathParams.getStringParameter( PATH_PARAMETER_ID, null ) ) ) ) {

      String filePath = URLDecoder.decode( urlEncodedFilePath, LocaleHelper.UTF_8 );

      info( "createContent() called for file: " + filePath );

      getResponse().setContentType( getSettings().getMimeType() );
      getResponse().setHeader( "Cache-Control", "no-cache" );

      InputStream content = null;

      try {

        RepositoryFile file = getRepository().getFile( filePath );

        SimpleRepositoryFileData data = getRepository().getDataForRead( file.getId(), SimpleRepositoryFileData.class );

        writeOutAndFlush( getResponse().getOutputStream(), data.getInputStream() );

      } finally {
        IOUtils.closeQuietly( content );
      }
    }
  }

  public IUnifiedRepository getRepository() {
    return repository;
  }

  public void setRepository( IUnifiedRepository repository ) {
    this.repository = repository;
  }

  public IBasicPluginSettings getSettings() {
    return settings;
  }

  public void setSettings( IBasicPluginSettings settings ) {
    this.settings = settings;
  }

  public boolean isEditMode() {
    return editMode;
  }

  public void setEditMode( boolean editMode ) {
    this.editMode = editMode;
  }

  protected HttpServletResponse getResponse() {
    return ( HttpServletResponse ) parameterProviders.get( PATH_PARAMETER_ID ).getParameter( "httpresponse" );
  }

  private void writeOutAndFlush( OutputStream out, InputStream data ) {
    try {
      IOUtils.copy( data, out );
      out.flush();
    } catch (IOException ex){
      getLogger().error( ex );
    }
  }
}
