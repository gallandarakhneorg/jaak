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
package io.sarl.jaak.util;

import java.util.Random;

/**
 * Central random number generator.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class RandomNumber {

	private static final Random RANDOM = new Random();

	private RandomNumber() {
		//
	}

	/** Replies the next randomly selected integer number.
	 * All 2<sup>32</sup> possible int values are produced with
	 * (approximately) equal probability.
	 *
	 * @return a random integer.
	 */
	public static int nextInt() {
		return RANDOM.nextInt();
	}

	/** Replies the next randomly selected integer number between
	 * 0 and n.
	 * All n possible int values are produced with (approximately)
	 * equal probability.
	 *
	 * @param n - the maximal value for the random integer.
	 * @return a random integer.
	 */
	public static int nextInt(int n) {
		return RANDOM.nextInt(n);
	}

	/** Replies the next randomly selected floating-point number between
	 * 0 and 1.
	 * All possible float values are produced with (approximately)
	 * equal probability.
	 *
	 * @return a random float.
	 */
	public static float nextFloat() {
		return RANDOM.nextFloat();
	}

	/** Replies the next randomly selected floating-point number between
	 * 0 and 1.
	 * All possible float values are produced with (approximately)
	 * equal probability.
	 *
	 * @return a random float.
	 */
	public static double nextDouble() {
		return RANDOM.nextDouble();
	}

	/** Replies the next randomly selected boolean value.
	 * The two possible values are produced with
	 * (approximately) equal probability.
	 *
	 * @return a random integer.
	 */
	public static boolean nextBoolean() {
		return RANDOM.nextBoolean();
	}

}
