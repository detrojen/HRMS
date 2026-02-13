export type TApplicantsBasicDetail = {
    applicantsName : string,
    applicantsEmail : string,
    applicantsPhone : string,
    details : string,
}

export type TReferJobRequest = {
    basicDetail : TApplicantsBasicDetail
    cv: File
}