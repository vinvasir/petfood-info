import { NgModule } from '@angular/core';

import { PetInfoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PetInfoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PetInfoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PetInfoSharedCommonModule {}
