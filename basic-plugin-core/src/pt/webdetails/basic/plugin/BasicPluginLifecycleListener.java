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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IPlatformReadyListener;
import org.pentaho.platform.api.engine.IPluginLifecycleListener;
import org.pentaho.platform.api.engine.IPluginProvider;
import org.pentaho.platform.api.engine.PluginLifecycleException;

public class BasicPluginLifecycleListener implements IPluginLifecycleListener, IPlatformReadyListener {

  protected static Log logger = LogFactory.getLog( BasicPluginLifecycleListener.class );

  /**
   * Called just prior to the plugin being registered with the platform. Note: This event does *not* precede the
   * detection of the plugin by any {@link IPluginProvider}s
   *
   * @throws PluginLifecycleException
   *           if an error occurred
   */
  @Override
  public void init() throws PluginLifecycleException {
    logger.info( "init()" );
  }

  /**
   * Called after the plugin has been registered with the platform, i.e. all content generators, components, etc.
   * have been loaded.
   *
   * @throws PluginLifecycleException
   *           if an error occurred
   */
  @Override
  public void loaded() throws PluginLifecycleException {
    logger.info( "loaded()" );
  }

  /**
   * Called when the plugin needs to be unloaded. This method should release all resources and return things to a
   * pre-loaded state.
   *
   * @throws PluginLifecycleException
   *           if an error occurred
   */
  @Override
  public void unLoaded() throws PluginLifecycleException {
    logger.info( "unloaded()" );
  }

  /**
   * Called after the platform has been booted and is ready to receive requests.  All plugins have been
   * initialized and loaded, spring has been loaded and all beans are ready.  All components and sub
   * systems have been started - scheduler/repository/reporting/mondrian - etc.
   *
   * @throws PluginLifecycleException
   *           if an error occurred
   */
  @Override
  public void ready() throws PluginLifecycleException {
    logger.info( "ready()" );
  }
}
