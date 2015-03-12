/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.db.api;

import org.assertj.db.api.navigation.WithChanges;
import org.assertj.db.api.origin.OriginWithChanges;

/**
 * Abstract class that represents a assert with an origin assert and which is the origin assert of another assert and have changes.
 *
 * @param <E> The "self" type of this assertion class. Please read &quot;<a href="http://bit.ly/anMa4g"
 *            target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>&quot;
 *            for more details.
 * @param <O> The class of the assert of origin
 * @author Régis Pouiller
 */
public abstract class AbstractAssertWithOriginWithChanges<E extends AbstractAssertWithOriginWithChanges<E, O>, O extends OriginWithChanges>
        extends AbstractAssertWithOrigin<E, O> implements WithChanges {

  /**
   * Constructor.
   *
   * @param selfType     Class of this assert : a sub-class of {@code AbstractAssertWithOriginWithChanges}.
   * @param originAssert The assert of origin.
   */
  AbstractAssertWithOriginWithChanges(Class<E> selfType, O originAssert) {
    super(selfType, originAssert);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofAll() {
    return origin.ofAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofCreation() {
    return origin.ofCreation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofModification() {
    return origin.ofModification();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofDeletion() {
    return origin.ofDeletion();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofCreationOnTable(String tableName) {
    return origin.ofCreationOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofModificationOnTable(String tableName) {
    return origin.ofModificationOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert ofDeletionOnTable(String tableName) {
    return origin.ofDeletionOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangesAssert onTable(String tableName) {
    return origin.onTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert change() {
    return origin.change();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert change(int index) {
    return origin.change(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfCreation() {
    return origin.changeOfCreation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfCreation(int index) {
    return origin.changeOfCreation(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfModification() {
    return origin.changeOfModification();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfModification(int index) {
    return origin.changeOfModification(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfDeletion() {
    return origin.changeOfDeletion();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfDeletion(int index) {
    return origin.changeOfDeletion(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOnTable(String tableName) {
    return origin.changeOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOnTable(String tableName, int index) {
    return origin.changeOnTable(tableName, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOnTableWithPks(String tableName, Object... pksValues) {
    return origin.changeOnTableWithPks(tableName, pksValues);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfCreationOnTable(String tableName) {
    return origin.changeOfCreationOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfCreationOnTable(String tableName, int index) {
    return origin.changeOfCreationOnTable(tableName, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfModificationOnTable(String tableName) {
    return origin.changeOfModificationOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfModificationOnTable(String tableName, int index) {
    return origin.changeOfModificationOnTable(tableName, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfDeletionOnTable(String tableName) {
    return origin.changeOfDeletionOnTable(tableName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChangeAssert changeOfDeletionOnTable(String tableName, int index) {
    return origin.changeOfDeletionOnTable(tableName, index);
  }
}