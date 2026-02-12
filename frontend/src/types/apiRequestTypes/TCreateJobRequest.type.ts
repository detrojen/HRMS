export type TCreateJobDetail = {
    id: number,
    title: string,
    description: string,
    workMode: string,
    skills: string[],
    vacancy: number,
    reviewerIds: number[],
    hrOwnerId: number,
}
export type TCreateJobRequest = {
    jobDetail: TCreateJobDetail,
    jdDocument: File
}