package com.hrms.backend.emailTemplates;

import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelMinDetailResponseDto;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelDocument;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class TravelAndExpenseEmailTemplates {
    public static String forTravelDetail(Travel travel){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(travel.getDescripton());
        String details = HtmlRenderer.builder().build().render(document);
        return new StringBuilder("Hello all\n\n")
                .append(travel.getTitle())
                .append("intiated by:- "+travel.getInitiatedBy().getFullName()+"\n\n")
                .append("details:- \n" + details)
                .append("\n\n")
                .append("travel is planned for " + travel.getStartDate() + " - " + travel.getEndDate())
                .append("\nMaximum $"+travel.getMaxReimbursementAmountPerDay()+" will be reimbersed")
                .append("\nLast date to add detail of expense is" + travel.getLastDateToSubmitExpense())
                .toString();
    }

    public static String forUpdatedTravelDetail(Travel travel){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(travel.getDescripton());
        String details = HtmlRenderer.builder().build().render(document);
        return new StringBuilder("Hello all\n\n")
                .append(travel.getTitle() + " : it's details is updated and new details are given below")
                .append("\n\nintiated by:- "+travel.getInitiatedBy().getFullName())
                .append("\n\ndetails:- \n" + details)
                .append("\n\ntravel is planned for " + travel.getStartDate() + " - " + travel.getEndDate())
                .append("\nMaximum $"+travel.getMaxReimbursementAmountPerDay()+" will be reimbersed")
                .append("\nLast date to add detail of expense is" + travel.getLastDateToSubmitExpense())
                .toString();
    }

    public static String forTravleDocumnet(TravelDocumentResponseDto document, TravelMinDetailResponseDto travel){
        return new StringBuilder("Hello,")
                .append("\n\nFor travel - " )
                .append(travel.getTitle() + " ( "  + travel.getStartDate() + " - " + travel.getEndDate())
                .append(document.getUploadedBy().getFirstName() + " " + document.getUploadedBy().getLastName())
                .append("has uploaded document for "+ document.getType())
                .append("\n\nhere that documnet attached and you can see that documnet in portal as well.")
                .toString();
    }
//
    public static String forEmployeeDocumnetUpload(TravelDocumentResponseDto document,  TravelMinDetailResponseDto travel){
        return new StringBuilder("Hello,")
                .append("\n\nFor travel - " )
                .append(travel.getTitle() + " ( "  + travel.getStartDate() + " - " + travel.getEndDate())
                .append(document.getUploadedBy().getFirstName() + " " + document.getUploadedBy().getLastName())
                .append("has uploaded personal document for "+ document.getType())
                .append("\n\nhere that documnet attached and you can see that documnet in portal as well.")
                .toString();
    }

    public static String forExpense(TravelExpenseResponseDto expense,  TravelMinDetailResponseDto travel, boolean isUpdated){
        return new StringBuilder("Hello,")
                .append("\n\nFor travel - " )
                .append(travel.getTitle() + " ( "  + travel.getStartDate() + " - " + travel.getEndDate())
                .append(expense.getEmployee().getFirstName() + " " + expense.getEmployee().getLastName())
                .append("has "+(isUpdated?"updated" : "submitted")+" expense for date "+ expense.getDateOfExpense())
                .append("\n\nExpense details")
                .append("\n\nhere that proof attached and you can see that proof in portal as well.")
                .toString();
    }

    public static String forReviewExpense(TravelWiseExpense expense){
        return new StringBuilder("Hello,")
                .append("\n\nFor travel - " )
                .append(expense.getTravel().getTitle() + " ( "  + expense.getTravel().getStartDate() + " - " + expense.getTravel().getEndDate())
                .append(expense.getReviewedBy().getFullName())
                .append("has  reviewed and"+ (expense.getStatus().equals("rejected")? "rejected" : "approved $" + expense.getAprrovedAmount() + "for")+" your expense request of $"+ expense.getAskedAmount()+ "of date "+ expense.getDateOfExpense())
                .append("\n\nhere that proof attached and you can see that proof in portal as well.")
                .toString();
    }
}
