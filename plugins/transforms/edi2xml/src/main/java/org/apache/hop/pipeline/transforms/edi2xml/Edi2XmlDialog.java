/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.pipeline.transforms.edi2xml;

import org.apache.hop.core.Const;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.pipeline.PipelineMeta;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.BaseDialog;
import org.apache.hop.ui.core.widget.ComboVar;
import org.apache.hop.ui.core.widget.TextVar;
import org.apache.hop.ui.pipeline.transform.BaseTransformDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Edi2XmlDialog extends BaseTransformDialog {

  private static final Class<?> PKG = Edi2XmlMeta.class;

  private final Edi2XmlMeta input;

  private TextVar wXmlField;

  private ComboVar wEdiField;

  public Edi2XmlDialog(
      Shell parent, IVariables variables, Edi2XmlMeta transformMeta, PipelineMeta pipelineMeta) {
    super(parent, variables, transformMeta, pipelineMeta);
    input = transformMeta;
  }

  @Override
  public String open() {
    Shell parent = getParent();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
    PropsUi.setLook(shell);
    setShellImage(shell, input);

    ModifyListener lsMod = e -> input.setChanged();
    changed = input.hasChanged();

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = PropsUi.getFormMargin();
    formLayout.marginHeight = PropsUi.getFormMargin();

    shell.setLayout(formLayout);
    shell.setText(BaseMessages.getString(PKG, "Edi2Xml.Shell.Title"));

    int middle = props.getMiddlePct();
    int margin = PropsUi.getMargin();

    // TransformName line
    wlTransformName = new Label(shell, SWT.RIGHT);
    wlTransformName.setText(BaseMessages.getString(PKG, "System.TransformName.Label"));
    wlTransformName.setToolTipText(BaseMessages.getString(PKG, "System.TransformName.Tooltip"));
    PropsUi.setLook(wlTransformName);
    fdlTransformName = new FormData();
    fdlTransformName.left = new FormAttachment(0, 0);
    fdlTransformName.right = new FormAttachment(middle, -margin);
    fdlTransformName.top = new FormAttachment(0, margin);
    wlTransformName.setLayoutData(fdlTransformName);

    wTransformName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    wTransformName.setText(transformName);
    PropsUi.setLook(wTransformName);
    wTransformName.addModifyListener(lsMod);
    fdTransformName = new FormData();
    fdTransformName.left = new FormAttachment(middle, 0);
    fdTransformName.top = new FormAttachment(0, margin);
    fdTransformName.right = new FormAttachment(100, 0);
    wTransformName.setLayoutData(fdTransformName);

    // edifact field line
    Label wlEdiField = new Label(shell, SWT.RIGHT);
    wlEdiField.setText(BaseMessages.getString(PKG, "Edi2Xml.InputField.Label"));
    PropsUi.setLook(wlEdiField);
    FormData fdlEdiField = new FormData();
    fdlEdiField.left = new FormAttachment(0, 0);
    fdlEdiField.right = new FormAttachment(middle, -margin);
    fdlEdiField.top = new FormAttachment(wTransformName, margin);
    wlEdiField.setLayoutData(fdlEdiField);

    wEdiField = new ComboVar(variables, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    wEdiField.setToolTipText(BaseMessages.getString(PKG, "Edi2Xml.InputField.Tooltip"));
    PropsUi.setLook(wEdiField);
    wEdiField.addModifyListener(lsMod);
    FormData fdEdiField = new FormData();
    fdEdiField.left = new FormAttachment(middle, 0);
    fdEdiField.top = new FormAttachment(wTransformName, margin);
    fdEdiField.right = new FormAttachment(100, 0);
    wEdiField.setLayoutData(fdEdiField);
    wEdiField.addFocusListener(
        new FocusListener() {
          @Override
          public void focusLost(FocusEvent e) {
            // Do nothing
          }

          @Override
          public void focusGained(FocusEvent e) {
            Cursor busy = new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT);
            shell.setCursor(busy);
            BaseTransformDialog.getFieldsFromPrevious(
                variables, wEdiField, pipelineMeta, transformMeta);
            shell.setCursor(null);
            busy.dispose();
          }
        });

    // xml output field value
    // output field name
    Label wlXmlField = new Label(shell, SWT.RIGHT);
    wlXmlField.setText(BaseMessages.getString(PKG, "Edi2Xml.OutputField.Label"));
    PropsUi.setLook(wlXmlField);
    FormData fdlXmlField = new FormData();
    fdlXmlField.left = new FormAttachment(0, 0);
    fdlXmlField.right = new FormAttachment(middle, -margin);
    fdlXmlField.top = new FormAttachment(wEdiField, margin);
    wlXmlField.setLayoutData(fdlXmlField);

    wXmlField = new TextVar(variables, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    PropsUi.setLook(wXmlField);
    wXmlField.setToolTipText(BaseMessages.getString(PKG, "Edi2Xml.OutputField.Tooltip"));
    wXmlField.addModifyListener(lsMod);
    FormData fdXmlField = new FormData();
    fdXmlField.left = new FormAttachment(middle, 0);
    fdXmlField.right = new FormAttachment(100, 0);
    fdXmlField.top = new FormAttachment(wEdiField, margin);
    wXmlField.setLayoutData(fdXmlField);

    // OK and cancel buttons
    wOk = new Button(shell, SWT.PUSH);
    wOk.setText(BaseMessages.getString(PKG, "System.Button.OK"));
    wCancel = new Button(shell, SWT.PUSH);
    wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

    BaseTransformDialog.positionBottomButtons(
        shell, new Button[] {wOk, wCancel}, margin, wXmlField);

    // Add listeners
    wCancel.addListener(SWT.Selection, e -> cancel());
    wOk.addListener(SWT.Selection, e -> ok());

    getData();

    BaseDialog.defaultShellHandling(shell, c -> ok(), c -> cancel());

    return transformName;
  }

  // Read data and place it in the dialog
  public void getData() {
    wXmlField.setText(Const.NVL(input.getOutputField(), ""));
    wEdiField.setText(Const.NVL(input.getInputField(), ""));

    wTransformName.selectAll();
    wTransformName.setFocus();
  }

  private void cancel() {
    transformName = null;
    input.setChanged(changed);
    dispose();
  }

  // let the plugin know about the entered data
  private void ok() {
    transformName = wTransformName.getText(); // return value
    input.setOutputField(wXmlField.getText());
    input.setInputField(wEdiField.getText());
    dispose();
  }
}
