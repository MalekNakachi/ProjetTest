import { EnvService } from './env.service';

export const EnvServiceFactory = () => {
    // Create env

    var apiUrl ;
    const env = new EnvService();
   // const resultwebservice =    window.__env ;
    // const resultwebservice =env.getServeurApplication("Serveur Application").subscribe((data:any)=>{
    // return data ;
    // }) ;
   // console.log("resultwebservice===> ",resultwebservice) ;


    // Read environment variables from browser window
    const browserWindow = window || {};
    const browserWindowEnv = browserWindow['__env'] || {};





    // Assign environment variables from browser window to env
    // In the current implementation, properties from env.js overwrite defaults from the EnvService.
    // If needed, a deep merge can be performed here to merge properties instead of overwriting them.
    for (const key in browserWindowEnv) {
     //   console.log("Key ====>",browserWindowEnv.Key) ;



        if (browserWindowEnv.hasOwnProperty(key)) {
//key.valueOf();
            // browserWindowEnv[Key] ;
            /* for (let item of env) {


             }*/
            if ((env[key.valueOf()]).valueOf() === '') {


                env[key] = window['__env'][key];

            }

        }




    }


    return env;
};

export const EnvServiceProvider = {
    provide: EnvService,
    useFactory: EnvServiceFactory,
    deps: [],
};
