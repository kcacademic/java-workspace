package com.sapient.learning;

public enum LeaveRequestState {
	
	SUBMITTED {
        @Override
        public LeaveRequestState nextState() {
            return ESCALATED;
        }
 
        @Override
        public String responsiblePerson() {
            return "Employee";
        }
    },
    ESCALATED {
        @Override
        public LeaveRequestState nextState() {
            return APPROVED;
        }
 
        @Override
        public String responsiblePerson() {
            return "Team Leader";
        }
    },
    APPROVED {
        @Override
        public LeaveRequestState nextState() {
            return this;
        }
 
        @Override
        public String responsiblePerson() {
            return "Department Manager";
        }
    };
 
    public abstract LeaveRequestState nextState(); 
    public abstract String responsiblePerson();
}