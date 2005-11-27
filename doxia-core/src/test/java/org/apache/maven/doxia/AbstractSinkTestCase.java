package org.apache.maven.doxia;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.doxia.module.apt.AptParser;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.PlexusTestCase;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id:AbstractSinkTestCase.java 348605 2005-11-24 12:02:44 +1100 (Thu, 24 Nov 2005) brett $
 */
public abstract class AbstractSinkTestCase
    extends PlexusTestCase
{
    protected Writer testWriter;

    // ---------------------------------------------------------------------
    // Test case
    // ----------------------------------------------------------------------

    public void testApt()
        throws Exception
    {
        AptParser parser = new AptParser();

        parser.parse( getTestReader(), createSink() );
    }

    // ----------------------------------------------------------------------
    // Abstract methods the individual SinkTests must provide
    // ----------------------------------------------------------------------

    protected abstract String outputExtension();

    protected abstract Sink createSink()
        throws Exception;

    // ----------------------------------------------------------------------
    // Methods for creating the test reader and writer
    // ----------------------------------------------------------------------

    protected Writer getTestWriter()
        throws Exception
    {
        if ( testWriter == null )
        {
            File outputDirectory = new File( getBasedirFile(), "target/output" );

            if ( !outputDirectory.exists() )
            {
                outputDirectory.mkdirs();
            }

            testWriter = new FileWriter( new File( outputDirectory, "test." + outputExtension() ) );
        }

        return testWriter;
    }

    protected Reader getTestReader()
        throws Exception
    {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( "test/test.apt" );

        InputStreamReader reader = new InputStreamReader( is );

        return reader;
    }

    // ----------------------------------------------------------------------
    // Utility methods
    // ----------------------------------------------------------------------

    public File getBasedirFile()
    {
        return new File( getBasedir() );
    }
}
