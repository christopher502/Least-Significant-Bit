import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '36eec818a944aaae7420b447e171d7b9997e2f766be8026597ce14e4aae29a8d') {
    pending.push(import('./chunks/chunk-068624e1cb60d5543629fa2af62c2790667a7f37fbb2b02f6add7f6fb34b0641.js'));
  }
  if (key === 'cd4924141ba10284aa940fdacb24a08b6c564f3687c4afd3212686bac2b91890') {
    pending.push(import('./chunks/chunk-25987efe5632fff434e05c531e9e427ac422df28f1561a0981f0e4df82a821cd.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}