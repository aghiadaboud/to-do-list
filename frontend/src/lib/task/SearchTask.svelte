<script>
    import {createEventDispatcher} from "svelte";
    import SimpleButton from "../common/SimpleButton.svelte";
    const dispatch = createEventDispatcher();

    let searchAttributeOptions = [
        { text: 'Description', value: 'description' },
        { text: 'Execution Date', value: 'executionDate' },
        { text: 'Status', value: 'taskStatus' },
    ];

    let selectedSearchAttribute;
    let searchInput;

    const handleSubmit = () => {
        if (searchInput === undefined) {
            dispatch('resetFilter', {});
        } else {
            dispatch('searchTask', {searchAttribute: selectedSearchAttribute, searchInput: searchInput});
        }
    }

    const handleResetFilter = () => {
        selectedSearchAttribute = searchAttributeOptions[0].value;
        searchInput = undefined;
        dispatch('resetFilter', {});
    }
</script>

<div class="flex flex-col md:flex-row">
    <div class="w-full">
        <form on:submit|preventDefault={handleSubmit}>
            <div class="md:flex mb-4">
                <select bind:value={selectedSearchAttribute} on:change={() => searchInput = ''} class="flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-900 bg-gray-200 border border-gray-300 rounded-l-lg cursor-pointer hover:bg-gray-200 focus:ring-2 focus:outline-none focus:ring-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:focus:ring-gray-700 dark:text-white dark:border-gray-600">
                    {#each searchAttributeOptions as option}
                        <option class="inline-flex w-full px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" value={option.value}>
                            {option.text}
                        </option>
                    {/each}
                </select>

                <div class="relative w-full mt-1 md:mt-0">
                    <input bind:value={searchInput} autofocus type="search" class="p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 rounded-r-lg border-l-gray-50 border-l-2 border border-gray-300 focus:outline-none focus:ring-sky-500 focus:border-sky-500 dark:bg-gray-700 dark:border-l-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500" placeholder="Task Info...">
                    <button type="submit" class="absolute top-0 right-0 p-2.5 text-sm font-medium text-white bg-sky-500 rounded-r-lg border border-blue-700 focus:ring-2 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                        <svg aria-hidden="true" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                        <span class="sr-only">Search</span>
                    </button>
                </div>
            </div>
        </form>
    </div>
    <div class="whitespace-nowrap md:ml-2">
        <SimpleButton text="Reset Filter" on:click={handleResetFilter}></SimpleButton>
    </div>
</div>
