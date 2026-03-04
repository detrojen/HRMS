create procedure sp_getTopCandidate
	@gameSlotId int
as begin
	DECLARE @topCandidateId BIGINT
	select
	TOP 1
	@topCandidateId = sr.id
	from SlotRequest sr 
join GameSlot gs on gs.id = sr.gameSlot_id
join SlotRequestWiseEmployee swe on swe.slotRequest_id = sr.id
join EmployeeWiseGameInterest egi on egi.employee_id = swe.employee_id and egi.gameType_id = gs.gameType_id
where sr.gameSlot_id = @gameSlotId and sr.status = 'On Hold'
group by  sr.id
	, sr.createdAt
order by Count(*) desc, AVG(currentCyclesSlotConsumed)  asc , MIN(currentCyclesSlotConsumed) asc , MAX(currentCyclesSlotConsumed)  asc, sr.createdAt asc

select * from SlotRequest where id = @topCandidateId

end