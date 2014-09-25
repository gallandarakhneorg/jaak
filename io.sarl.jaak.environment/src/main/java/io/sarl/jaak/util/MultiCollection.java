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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A collection composed of collections.
 * <p>
 * This collection is not thread-safe.
 * <p>
 * This collection is read-only.
 * 
 * FIXME: Replace by the AFC or Guava equivalent.
 * 
 * @param <E> is the type of elements in the collections.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class MultiCollection<E>
implements Collection<E> {
	
	private final List<Collection<? extends E>> collections = new ArrayList<Collection<? extends E>>();
	
	/**
	 */
	public MultiCollection() {
		//
	}

	/** Add a collection inside this multicollection.
	 * 
	 * @param collection
	 */
	public void addCollection(Collection<? extends E> collection) {
		if (collection!=null && !collection.isEmpty()) {
			this.collections.add(collection);
		}
	}
	
	/** Remove a collection from this multicollection.
	 * 
	 * @param collection
	 * @return <code>true</code> if the multi-collection has changed,
	 * otherwise <code>false</code>.
	 */
	public boolean removeCollection(Collection<? extends E> collection) {
		return this.collections.remove(collection);
	}

	/** {@inheritDoc}
	 */
	@Override
	public void clear() {
		this.collections.clear();
	}

	/**
	 * This function is not supported, see {@link #addCollection(Collection)}.
	 */
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This function is not supported, see {@link #addCollection(Collection)}.
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object o) {
		for(Collection<? extends E> c : this.collections) {
			if (c.contains(o)) return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object o : c) {
			if (!contains(o)) return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		for(Collection<? extends E> c : this.collections) {
			if (!c.isEmpty()) return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<E> iterator() {
		return new MultiIterator<E>(this.collections.iterator());
	}

	/**
	 * This function is not supported, see {@link #removeCollection(Collection)}.
	 */
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This function is not supported, see {@link #removeCollection(Collection)}.
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This function is not supported, see {@link #removeCollection(Collection)}.
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		int t = 0;
		for(Collection<? extends E> c : this.collections) {
			t += c.size();
		}
		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param <E> is the type of elements in the collections.
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static class MultiIterator<E> implements Iterator<E> {
		
		private final Iterator<Collection<? extends E>> iterator;
		private Iterator<? extends E> currentIterator;
		
		/**
		 * @param i
		 */
		public MultiIterator(Iterator<Collection<? extends E>> i) {
			this.iterator = i;
			searchNext();
		}
		
		private void searchNext() {
			if (this.currentIterator==null || !this.currentIterator.hasNext()) {
				this.currentIterator = null;
				while (this.currentIterator==null && this.iterator.hasNext()) {
					Collection<? extends E> iterable = this.iterator.next();
					Iterator<? extends E> iter = iterable.iterator();
					if (iter.hasNext()) {
						this.currentIterator = iter;
					}
				}
			}
		}

		@Override
		public boolean hasNext() {
			return this.currentIterator!=null && this.currentIterator.hasNext();
		}

		@Override
		public E next() {
			if (this.currentIterator==null) throw new NoSuchElementException();
			E n = this.currentIterator.next();
			searchNext();
			return n;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		
	}
	
}
