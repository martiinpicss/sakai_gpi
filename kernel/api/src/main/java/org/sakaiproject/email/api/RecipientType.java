/**
 * Copyright (c) 2003-2008 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.email.api;

/**
 * Type safe constant for recipient types
 */
public enum RecipientType
{
	// recipients to be marked in the "to" header
	TO,
	// recipients to be marked in the "cc" header
	CC,
	// recipients to be marked in the "bcc" header
	BCC,
	// actual recipients of message. if specified, other recipients are marked in the headers
	// but not used in the SMTP transport.
	ACTUAL
}