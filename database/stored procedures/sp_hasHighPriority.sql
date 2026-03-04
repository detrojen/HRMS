ALTER procedure sp_hasHighPriority 
@newRequestId BIGINT,
@confirmRequestId BIGINT

as begin
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	DECLARE @resultId BIGINT
	select
		top 1 @resultId = sr.id
	from SlotRequest sr 
join GameSlot gs on gs.id = sr.gameSlot_id
join SlotRequestWiseEmployee swe on swe.slotRequest_id = sr.id
join EmployeeWiseGameInterest egi on egi.employee_id = swe.employee_id and egi.gameType_id = gs.gameType_id
where sr.id in (@confirmRequestId,@newRequestId)
group by  sr.id
order by Count(*) desc, AVG(currentCyclesSlotConsumed)  asc , MIN(currentCyclesSlotConsumed) asc , MAX(currentCyclesSlotConsumed)  asc

SELECT 
	CASE when @resultId = @confirmRequestId
		then 0
		else 1
	END
	
end