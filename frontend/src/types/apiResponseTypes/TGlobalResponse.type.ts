export  type TGlobalResponse<T> = {
     data : T,
     status : number,
     message : string,
     authToken : string,
     errors : any
}