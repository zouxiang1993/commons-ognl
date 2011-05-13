/*
 * $Id$
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.ognl;

import java.util.List;
import java.util.Map;

/**
 * Implementation of PropertyAccessor that uses reflection on the target object's class to find a
 * field or a pair of set/get methods with the given property name.
 *
 * @author Luke Blanshard (blanshlu@netscape.net)
 * @author Drew Davidson (drew@ognl.org)
 */
public class ObjectMethodAccessor implements MethodAccessor
{

    /* MethodAccessor interface */
    public Object callStaticMethod(Map context, Class targetClass, String methodName, Object[] args)
            throws MethodFailedException
    {
        List methods = OgnlRuntime.getMethods(targetClass, methodName, true);

        return OgnlRuntime.callAppropriateMethod((OgnlContext) context, targetClass,
                                                 null, methodName, null, methods, args);
    }

    public Object callMethod(Map context, Object target, String methodName, Object[] args)
            throws MethodFailedException
    {
        Class targetClass = (target == null) ? null : target.getClass();
        List methods = OgnlRuntime.getMethods(targetClass, methodName, false);

        if ((methods == null) || (methods.size() == 0))
        {
            methods = OgnlRuntime.getMethods(targetClass, methodName, true);

        }

        return OgnlRuntime.callAppropriateMethod((OgnlContext) context, target,
                                                 target, methodName, null, methods, args);
    }
}