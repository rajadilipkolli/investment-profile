export class Sip {
    public amount: number;
    public ror: number;
    public duration: number;
    public investment: number;
    public predictedReturn: number;

    public constructor(amount: number, ror: number, duration: number, investment: number, predictedReturn: number) {
        this.amount = amount;
        this.ror = ror;
        this.duration = duration;
        this.investment = investment;
        this.predictedReturn = predictedReturn;
    }
}