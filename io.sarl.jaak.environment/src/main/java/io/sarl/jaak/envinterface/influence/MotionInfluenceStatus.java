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
package io.sarl.jaak.envinterface.influence;

/** Status of the last emitted motion influence.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public enum MotionInfluenceStatus {

	/** The status is not available.
	 */
	NOT_AVAILABLE,

	/** The influence was completely applied, ie. the motion was
	 * proceeded entirely.
	 */
	COMPLETE_MOTION,
	
	/** The motion was stopped in such a way that the turtle
	 * move on a distance strictly smaller than the influence request.
	 */
	PARTIAL_MOTION,
	
	/** The motion was completely discarted in such a way that the turtle
	 * has not moved.
	 */
	NO_MOTION;

	/** Replies if the status corresponds to a failure.
	 * 
	 * @return <code>true</code> if no motion or status not available.
	 */
	public boolean isFailure() {
		return this==NO_MOTION || this==NOT_AVAILABLE;
	}
	
	/** Replies if the status corresponds to a success.
	 * 
	 * @return <code>true</code> if complete or partial motion.
	 */
	public boolean isSuccess() {
		return this==PARTIAL_MOTION|| this==COMPLETE_MOTION;
	}

}