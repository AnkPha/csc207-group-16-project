package use_case.filter;

/**
 * The Filter Use Case.
 */
public interface FilterInputBoundary {

    /**
     * Execute the Filter Use Case.
     * @param filterInputData the input data for this use case
     **/
    void execute(FilterInputData filterInputData);
}
