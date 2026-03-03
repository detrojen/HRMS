export type TSelfResponse = {
    id:  number,
    firstName:  string,
    lastName:  string,
    email:  string,
    role:  string,
    roles: {
        id:number,
        roleTitle:string
    }[]
}