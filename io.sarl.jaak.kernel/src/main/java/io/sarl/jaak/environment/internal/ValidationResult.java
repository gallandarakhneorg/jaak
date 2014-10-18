/*
 * $Id$
 *
 * Jaak environment model is an open-source multiagent library.
 * More details on http://www.sarl.io
 *
 * Copyright (C) 2014 Stéphane GALLAND.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sarl.jaak.environment.internal;

/** This enumeration indicates how a point was validated against the
 * grid coordinate system.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum ValidationResult {

	/** Point coordinates have not changed.
	 */
	NO_CHANGE,

	/** Point coordinates have been discarded because theyare outside the grid coordinate system.
	 */
	DISCARDED,

	/** Point coordinates have been clipped to the grid coordinate system.
	 */
	CLIPPED,

	/** Point coordinates have been wrapped to reach the other side of the grid coordinate system.
	 */
	WRAPPED;

}
