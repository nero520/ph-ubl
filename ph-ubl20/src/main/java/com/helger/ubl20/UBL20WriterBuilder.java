/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.ubl20;

import javax.annotation.Nonnull;
import javax.xml.transform.Result;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.state.ESuccess;
import com.helger.commons.xml.namespace.MapBasedNamespaceContext;
import com.helger.ubl.api.AbstractUBLDocumentMarshaller;
import com.helger.ubl.api.builder.AbstractUBLWriterBuilder;

/**
 * A writer builder for UBL 2.0 documents.
 *
 * @author Philip Helger
 * @param <T>
 *          The UBL 2.0 implementation class to be read
 */
public class UBL20WriterBuilder <T> extends AbstractUBLWriterBuilder <T, UBL20WriterBuilder <T>>
{
  public UBL20WriterBuilder (@Nonnull final Class <T> aClass)
  {
    super (UBL20DocumentTypes.getDocumentTypeOfImplementationClass (aClass));

    // Set global event handler
    setValidationEventHandler (AbstractUBLDocumentMarshaller.getGlobalValidationEventHandler ());

    // Create a special namespace context for the passed document type
    final MapBasedNamespaceContext aNSContext = new MapBasedNamespaceContext ();
    aNSContext.addMappings (UBL20NamespaceContext.getInstance ());
    aNSContext.setDefaultNamespaceURI (m_aDocType.getNamespaceURI ());
    setNamespaceContext (aNSContext);
  }

  @Override
  @Nonnull
  public ESuccess write (@Nonnull final T aUBLDocument, @Nonnull final Result aResult)
  {
    ValueEnforcer.notNull (aUBLDocument, "UBLDocument");
    return UBL20Marshaller.writeUBLDocument (aUBLDocument,
                                             m_aClassLoader,
                                             (EUBL20DocumentType) m_aDocType,
                                             m_aEventHandler,
                                             m_aNSContext,
                                             aResult);
  }

  /**
   * Create a new writer builder.
   *
   * @param aClass
   *          The UBL class to be written. May not be <code>null</code>.
   * @return The new writer builder. Never <code>null</code>.
   */
  @Nonnull
  public static <T> UBL20WriterBuilder <T> create (@Nonnull final Class <T> aClass)
  {
    return new UBL20WriterBuilder <T> (aClass);
  }
}